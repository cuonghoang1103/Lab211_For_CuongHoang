package service;

import java.util.Comparator;
import model.Asset;

/**
 * STRATEGY: asset name Z -> A (the brief's "descending"), id as tie-breaker.
 *
 * @author HE176322
 */
public class AssetNameComparator implements Comparator<Asset> {

    // Creates the strategy; it keeps no state.
    public AssetNameComparator() {
    }

    // Negative when first comes before second: the bigger name first.
    @Override
    public int compare(Asset first, Asset second) {
        int byName = second.getName().compareToIgnoreCase(first.getName());

        // same name: the id decides, so the order never changes between two runs
        if (byName == 0) {
            return first.getAssetId().compareToIgnoreCase(second.getAssetId());
        }

        return byName;
    }
}
