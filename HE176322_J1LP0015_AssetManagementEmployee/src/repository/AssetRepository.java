package repository;

import constants.Constants;
import model.Asset;

/**
 * REPOSITORY: asset.dat - "A001, Samsung projector, White, 500.0, 3.2, 10".
 *
 * @author HE176322
 */
public class AssetRepository extends FileRepository<Asset> {

    // Creates the store of asset.dat: six columns.
    public AssetRepository() {
        super(Constants.ASSET_FILE, Constants.ASSET_COLUMNS);
    }

    // Six columns: id, name, color, price, weight, quantity.
    @Override
    protected Asset parse(String[] partArray) {
        return new Asset(partArray[Constants.ASSET_ID], partArray[Constants.ASSET_NAME],
                partArray[Constants.ASSET_COLOR],
                Double.parseDouble(partArray[Constants.ASSET_PRICE]),
                Double.parseDouble(partArray[Constants.ASSET_WEIGHT]),
                Integer.parseInt(partArray[Constants.ASSET_QUANTITY]));
    }

    // The same six columns, joined with ", ".
    @Override
    protected String format(Asset asset) {
        return String.join(Constants.DATA_JOINER, asset.getAssetId(), asset.getName(),
                asset.getColor(), String.valueOf(asset.getPrice()),
                String.valueOf(asset.getWeight()), String.valueOf(asset.getQuantity()));
    }
}
