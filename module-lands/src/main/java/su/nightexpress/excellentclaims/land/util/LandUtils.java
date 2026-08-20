package su.nightexpress.excellentclaims.land.util;

import org.jspecify.annotations.NullMarked;

import su.nightexpress.nightcore.util.geodata.pos.ChunkPos;

@NullMarked
public final class LandUtils {

    private LandUtils() {
    }

    public static ChunkPos[] getAdjacentChunks(ChunkPos chunk) {
        int x = chunk.getX();
        int z = chunk.getZ();

        return new ChunkPos[]{
            new ChunkPos(x + 1, z),
            new ChunkPos(x - 1, z),
            new ChunkPos(x, z + 1),
            new ChunkPos(x, z - 1)
        };
    }

    public static boolean areChunksConnected(ChunkPos first, ChunkPos second) {
        int dx = Math.abs(first.getX() - second.getX());
        int dz = Math.abs(first.getZ() - second.getZ());

        return (dx == 1 && dz == 0) || (dx == 0 && dz == 1);
    }
}
