package service;

import constants.Message;
import dto.AssetRequestDTO;
import dto.AssetResponseDTO;
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
    // Order of the search result: name descending.
    private Comparator<Asset> nameOrder;

    // Creates the service on asset.dat.
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
        this.nameOrder = new AssetNameComparator();
    }

    // Reads asset.dat.
    public void loadAssets() throws Exception {
        assetRepository.load();
    }

    // Function 2: every asset whose name contains the text, name descending.
    public ArrayList<AssetResponseDTO> searchByName(AssetRequestDTO requestDTO)
            throws Exception {
        String text = requestDTO.getKeyword().toLowerCase();
        ArrayList<Asset> found = new ArrayList<>();
        // keep the assets whose name contains the text, ignoring case
        for (Asset asset : assetRepository.findAll()) {
            // "pro" finds "Samsung projector" and "Macbook pro 2016"
            if (asset.getName().toLowerCase().contains(text)) {
                found.add(asset);
            }
        }
        // nothing matched
        if (found.isEmpty()) {
            throw new Exception(Message.NOT_FOUND);
        }
        Collections.sort(found, nameOrder);
        return toResponseList(found);
    }

    // Function 3, first step: the brief's "Show list of asset (asset.dat file)".
    public ArrayList<AssetResponseDTO> getAllAssets() throws Exception {
        // nothing to borrow
        if (assetRepository.isEmpty()) {
            throw new Exception(Message.NO_ASSET);
        }
        return toResponseList(assetRepository.findAll());
    }

    // Copies assets into rows for the view.
    private ArrayList<AssetResponseDTO> toResponseList(ArrayList<Asset> assets) {
        ArrayList<AssetResponseDTO> rows = new ArrayList<>();
        // one row per asset, same order
        for (Asset asset : assets) {
            AssetResponseDTO row = new AssetResponseDTO();
            row.setAssetID(asset.getAssetID());
            row.setName(asset.getName());
            row.setColor(asset.getColor());
            row.setPrice(asset.getPrice());
            row.setWeight(asset.getWeight());
            row.setQuantity(asset.getQuantity());
            rows.add(row);
        }
        return rows;
    }
}
