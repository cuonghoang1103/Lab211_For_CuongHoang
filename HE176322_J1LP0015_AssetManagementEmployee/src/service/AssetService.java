package service;

import constants.Message;
import dto.AssetDTO;
import dto.AssetRequestDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Asset;
import repository.AssetRepository;

/**
 * SERVICE: Function 2 and the asset list of Function 3.
 *
 * @author HE176322
 */
public class AssetService {

    // asset.dat.
    private AssetRepository assetRepository;

    // Order of the search result: name descending (Strategy).
    private Comparator<Asset> nameOrder;

    // Creates the service on asset.dat (constructor injection).
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
        this.nameOrder = new AssetNameComparator();
    }

    // Start-up: the lines main read from asset.dat become assets.
    public void loadData(AssetRequestDTO requestDTO) {
        assetRepository.loadData(requestDTO.getAssetLineList());
    }

    // Function 2: every asset whose name contains the text, name descending.
    public ArrayList<AssetDTO> searchByName(AssetRequestDTO requestDTO) throws Exception {
        String text = requestDTO.getKeyword().toLowerCase();
        ArrayList<Asset> foundList = new ArrayList<>();

        // keep the assets whose name contains the text, ignoring case
        for (Asset asset : assetRepository.findAll()) {
            // "pro" finds "Samsung projector" and "Macbook pro 2016"
            if (asset.getName().toLowerCase().contains(text)) {
                foundList.add(asset);
            }
        }

        // nothing matched
        if (foundList.isEmpty()) {
            throw new Exception(Message.NOT_FOUND);
        }

        // the matches, name descending
        Collections.sort(foundList, nameOrder);
        return toAssetDTOList(foundList);
    }

    // Function 3, before the asset is typed: the brief's "Show list of asset (asset.dat
    // file)".
    public ArrayList<AssetDTO> getAllAssets() throws Exception {
        // nothing to borrow
        if (assetRepository.isEmpty()) {
            throw new Exception(Message.NO_ASSET);
        }

        return toAssetDTOList(assetRepository.findAll());
    }

    // Copies assets into rows for the view, same order.
    private ArrayList<AssetDTO> toAssetDTOList(ArrayList<Asset> assetList) {
        ArrayList<AssetDTO> assetDTOList = new ArrayList<>();

        // one row per asset
        for (Asset asset : assetList) {
            assetDTOList.add(toAssetDTO(asset));
        }

        return assetDTOList;
    }

    // Copies one asset into a row for the view (the controller never sees the model).
    private AssetDTO toAssetDTO(Asset asset) {
        AssetDTO assetDTO = new AssetDTO();

        // every column of the table
        assetDTO.setAssetId(asset.getAssetId());
        assetDTO.setName(asset.getName());
        assetDTO.setColor(asset.getColor());
        assetDTO.setPrice(asset.getPrice());
        assetDTO.setWeight(asset.getWeight());
        assetDTO.setQuantity(asset.getQuantity());
        return assetDTO;
    }
}
