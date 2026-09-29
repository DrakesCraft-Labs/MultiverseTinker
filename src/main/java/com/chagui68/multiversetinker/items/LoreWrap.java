package com.chagui68.multiversetinker.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Word-wraps item lore so no tooltip row ever runs off the screen.
 *
 * <p>Minecraft renders every lore entry as a single row: an entry wider than the client window is
 * simply clipped, which is what used to happen to the weapon lore, where the perk and trait
 * descriptions were whole sentences on one row. This helper splits such a row at word boundaries
 * into several rows while keeping every colour and decoration of the original components, so long
 * lore stays fully readable.</p>
 *
 * <p>Row width is measured with an approximation of the default Minecraft font: most ASCII glyphs
 * advance {@value #DEFAULT_CHAR_WIDTH} pixels, narrow glyphs such as {@code i} or {@code .} less and
 * non-ASCII glyphs (bullets, bars, stars) more. Over-estimating is deliberate — wrapping one word
 * early is harmless, wrapping too late clips the text again.</p>
 *
 * <p>Everything here is pure: no plugin instance, no registry and no side effect, so lore can be
 * wrapped anywhere an {@link net.kyori.adventure.text.Component} is built.</p>
 */
public final class LoreWrap {

    /** Visible width of one wrapped lore row, in default-font pixels. */
    public static final int DEFAULT_MAX_PIXELS = 190;

    /**
     * Width budget of the tier tag and progress bar at the top of a modular item's lore. Those two
     * rows are rewritten in place every time the item levels up, so they are allowed to stay wider —
     * what matters is that they are the same rows before and after the update.
     */
    public static final int HEADER_MAX_PIXELS = 320;

    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();
    private static final int DEFAULT_CHAR_WIDTH = 6;
    private static final int WIDE_CHAR_WIDTH = 8;
    /** How far wrapped rows are indented to line up with the original bullet. */
    private static final int MAX_HANGING_INDENT = 4;

    private LoreWrap() {
    }

    /** Wraps every row of a lore list at the default budget. */
    @Nonnull
    public static List<Component> wrapAll(@Nonnull List<Component> lines) {
        return wrapAll(lines, 0);
    }

    /**
     * Wraps every row of a lore list, giving the first {@code headerLines} rows the wider
     * {@link #HEADER_MAX_PIXELS} budget instead of {@link #DEFAULT_MAX_PIXELS}.
     */
    @Nonnull
    public static List<Component> wrapAll(@Nonnull List<Component> lines, int headerLines) {
        List<Component> wrapped = new ArrayList<>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            wrapped.addAll(wrap(lines.get(i), i < headerLines ? HEADER_MAX_PIXELS : DEFAULT_MAX_PIXELS));
        }
        return wrapped;
    }

    /** Wraps a single row at the default budget. */
    @Nonnull
    public static List<Component> wrap(@Nonnull Component line) {
        return wrap(line, DEFAULT_MAX_PIXELS);
    }

    /**
     * Wraps a single row at the given budget.
     *
     * @return the row split into as many rows as needed; a row that already fits is returned as-is,
     *         so short lore keeps its exact original components.
     */
    @Nonnull
    public static List<Component> wrap(@Nonnull Component line, int maxPixels) {
        String plain = PLAIN.serialize(line);
        if (plain.isEmpty() || pixelWidth(plain) <= maxPixels) {
            return List.of(line);
        }

        List<Token> tokens = tokenize(flatten(line));
        int indent = hangingIndent(plain);
        int indentPixels = indent * charWidth(' ');

        List<List<Token>> rows = new ArrayList<>();
        List<Token> current = new ArrayList<>();
        int width = 0;

        for (Token token : tokens) {
            if (token.space()) {
                // Indentation of the original row is kept on the first row only; the rows produced by
                // the wrap get their own hanging indent below.
                if (current.isEmpty() && !rows.isEmpty()) continue;
                current.add(token);
                width += charWidth(' ');
                continue;
            }

            if (hasContent(current) && width + token.width() > maxPixels) {
                rows.add(trimTrailingSpaces(current));
                current = new ArrayList<>();
                width = 0;
                if (indent > 0) {
                    current.add(spaceToken(indent, token.style()));
                    width = indentPixels;
                }
            }

            current.add(token);
            width += token.width();
        }
        rows.add(trimTrailingSpaces(current));

        List<Component> wrapped = new ArrayList<>(rows.size());
        for (List<Token> row : rows) {
            wrapped.add(toComponent(row));
        }
        return wrapped.isEmpty() ? List.of(line) : wrapped;
    }

    /** Approximate on-screen width of a plain string, in default-font pixels. */
    public static int pixelWidth(@Nonnull String text) {
        int width = 0;
        for (int i = 0; i < text.length(); i++) {
            width += charWidth(text.charAt(i));
        }
        return width;
    }

    /**
     * Width of a single glyph.
     *
     * <p>Wide characters are over-estimated rather than under-estimated: the progress bars, stars and
     * bullets used by the lore are not part of the default ASCII font and may be replaced by a wide
     * fallback glyph on the client.</p>
     */
    private static int charWidth(char c) {
        return switch (c) {
            case ' ' -> 4;
            case '!', '|', ',', '.', '\'', '`', 'i', 'l' -> 2;
            case '"', '(', ')', '[', ']', '{', '}', '<', '>', '*', ':', ';' -> 4;
            case '@' -> 7;
            default -> (c < 128) ? DEFAULT_CHAR_WIDTH : WIDE_CHAR_WIDTH;
        };
    }

    /** Flattens a component tree into styled text pieces, inheriting every parent style. */
    @Nonnull
    private static List<Piece> flatten(@Nonnull Component component) {
        List<Piece> pieces = new ArrayList<>();
        flattenInto(component, Style.empty(), pieces);
        return pieces;
    }

    private static void flattenInto(@Nonnull Component component, @Nonnull Style inherited, @Nonnull List<Piece> out) {
        Style style = inherited.merge(component.style());

        // Lore is built from text components; anything else (translatable, keybind, NBT...) is kept as
        // its rendered plain text so no content is ever dropped by the wrap.
        String text = (component instanceof TextComponent textComponent) ? textComponent.content() : "";
        if (text.isEmpty() && !(component instanceof TextComponent) && component.children().isEmpty()) {
            text = PLAIN.serialize(component);
        }
        if (!text.isEmpty()) {
            out.add(new Piece(text, style));
        }

        for (Component child : component.children()) {
            flattenInto(child, style, out);
        }
    }

    /** Splits styled pieces into word and space tokens; words are never broken mid-way. */
    @Nonnull
    private static List<Token> tokenize(@Nonnull List<Piece> pieces) {
        List<Token> tokens = new ArrayList<>();
        List<Piece> word = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        for (Piece piece : pieces) {
            for (int i = 0; i < piece.text().length(); i++) {
                char c = piece.text().charAt(i);
                if (c == ' ' || c == '\t' || c == '\n') {
                    if (buffer.length() > 0) {
                        word.add(new Piece(buffer.toString(), piece.style()));
                        buffer.setLength(0);
                    }
                    if (!word.isEmpty()) {
                        tokens.add(wordToken(word));
                        word = new ArrayList<>();
                    }
                    if (c == ' ') {
                        tokens.add(spaceToken(1, piece.style()));
                    }
                    continue;
                }
                buffer.append(c);
            }
            if (buffer.length() > 0) {
                word.add(new Piece(buffer.toString(), piece.style()));
                buffer.setLength(0);
            }
        }

        if (!word.isEmpty()) {
            tokens.add(wordToken(word));
        }
        return tokens;
    }

    /** Rebuilds one wrapped row, merging neighbouring pieces that share a style. */
    @Nonnull
    private static Component toComponent(@Nonnull List<Token> row) {
        TextComponent.Builder builder = Component.text();
        StringBuilder buffer = new StringBuilder();
        Style style = null;

        for (Token token : row) {
            for (Piece piece : token.pieces()) {
                if (style != null && !style.equals(piece.style())) {
                    builder.append(Component.text(buffer.toString(), style));
                    buffer.setLength(0);
                }
                style = piece.style();
                buffer.append(piece.text());
            }
        }
        if (buffer.length() > 0) {
            builder.append(Component.text(buffer.toString(), style == null ? Style.empty() : style));
        }
        return builder.build();
    }

    @Nonnull
    private static Token wordToken(@Nonnull List<Piece> pieces) {
        int width = 0;
        for (Piece piece : pieces) {
            width += pixelWidth(piece.text());
        }
        return new Token(List.copyOf(pieces), false, width);
    }

    @Nonnull
    private static Token spaceToken(int count, @Nonnull Style style) {
        String text = " ".repeat(count);
        return new Token(List.of(new Piece(text, style)), true, pixelWidth(text));
    }

    @Nonnull
    private static List<Token> trimTrailingSpaces(@Nonnull List<Token> row) {
        int end = row.size();
        while (end > 0 && row.get(end - 1).space()) {
            end--;
        }
        return end == row.size() ? row : new ArrayList<>(row.subList(0, end));
    }

    private static boolean hasContent(@Nonnull List<Token> row) {
        for (Token token : row) {
            if (!token.space()) return true;
        }
        return false;
    }

    /**
     * Hanging indent of a wrapped row: its own left padding plus the width of a leading bullet, so the
     * rows created by the wrap line up under the text instead of under the bullet. The lore prefixes
     * rows with {@code "✦ "} for section headers and {@code "  • "} for list entries.
     */
    private static int hangingIndent(@Nonnull String text) {
        int indent = 0;
        while (indent < text.length() && text.charAt(indent) == ' ') {
            indent++;
        }
        if (indent + 1 < text.length()
                && (text.charAt(indent) == '•' || text.charAt(indent) == '✦' || text.charAt(indent) == '◆')
                && text.charAt(indent + 1) == ' ') {
            indent += 2;
        }
        return Math.min(MAX_HANGING_INDENT, indent);
    }

    /** A run of text sharing one style. */
    private record Piece(@Nonnull String text, @Nonnull Style style) {
    }

    /**
     * One wrapping unit: either a word (which may itself be made of several styled pieces, e.g. a
     * gradient name) or a single space.
     */
    private record Token(@Nonnull List<Piece> pieces, boolean space, int width) {

        @Nonnull
        Style style() {
            return pieces.get(0).style();
        }
    }
}
