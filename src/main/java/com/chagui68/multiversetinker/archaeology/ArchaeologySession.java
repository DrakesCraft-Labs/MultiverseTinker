package com.chagui68.multiversetinker.archaeology;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import javax.annotation.Nonnull;
import java.util.UUID;

@Getter
public class ArchaeologySession {

    private final UUID playerUuid;
    private final Location blockLocation;
    private final BlockFace clickedFace;
    @Setter
    private int ticksProgress;
    @Setter
    private long lastInteractionTime;

    public ArchaeologySession(@Nonnull UUID playerUuid, @Nonnull Location blockLocation, @Nonnull BlockFace clickedFace) {
        this.playerUuid = playerUuid;
        this.blockLocation = blockLocation.getBlock().getLocation();
        this.clickedFace = clickedFace;
        this.ticksProgress = 0;
        this.lastInteractionTime = System.currentTimeMillis();
    }

    public void incrementProgress(int amount) {
        this.ticksProgress += amount;
        this.lastInteractionTime = System.currentTimeMillis();
    }

    public boolean isExpired(long maxIdleMillis) {
        return (System.currentTimeMillis() - lastInteractionTime) > maxIdleMillis;
    }
}
