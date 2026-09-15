package repository;

import constants.Constants;
import model.Asset;

/**
 * REPOSITORY: asset.dat - "A001, Samsung projector, White, 500.0, 3.2, 10".
 *
 * @author HE176322
 */
public class AssetRepository extends FileRepository<Asset> {

    // Creates the store of asset.dat.
    public AssetRepository() {
        super(Constants.ASSET_FILE);
    }

    // Six columns: id, name, color, price, weight, quantity.
    @Override
    protected Asset parse(String[] parts) throws Exception {
        // a line with too few or too many columns
        if (parts.length != Constants.ASSET_COLUMNS) {
            throw new Exception();
        }
        return new Asset(parts[Constants.ASSET_ID], parts[Constants.ASSET_NAME],
                parts[Constants.ASSET_COLOR], Double.parseDouble(parts[Constants.ASSET_PRICE]),
                Double.parseDouble(parts[Constants.ASSET_WEIGHT]),
                Integer.parseInt(parts[Constants.ASSET_QUANTITY]));
    }

    // The same six columns, joined with ", ".
    @Override
    protected String format(Asset asset) {
        return String.join(Constants.DATA_JOINER, asset.getAssetID(), asset.getName(),
                asset.getColor(), String.valueOf(asset.getPrice()),
                String.valueOf(asset.getWeight()), String.valueOf(asset.getQuantity()));
    }
}
