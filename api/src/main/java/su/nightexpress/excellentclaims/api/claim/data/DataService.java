package su.nightexpress.excellentclaims.api.claim.data;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import su.nightexpress.excellentclaims.api.claim.Claim;
import su.nightexpress.excellentclaims.api.core.id.Identifier;
import su.nightexpress.nightcore.bridge.key.AdaptedKey;

@NullMarked
public interface DataService<T extends Claim> extends DirtyFlagger<T> {

    void loadClaims(AdaptedKey worldKey);

    void unloadClaims(AdaptedKey worldKey);

    @Nullable
    T getClaim(Identifier id);

    void saveClaim(T claim);

    void editWithReindex(T claim, Runnable editAction);

    void deleteClaim(T claim);

    void markDirty(@NonNull T claim);

    void saveDirty();

    void saveAll();
}
