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
 * SERVICE: Functions 2, 3 and 4 - search, create and update assets.
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
        ArrayList<AssetResponseDTO> rows = new ArrayList<>();
        // one row per asset, in the sorted order
        for (Asset asset : found) {
            rows.add(toResponse(asset));
        }
        return rows;
    }

    // Function 3, first step: refuses an id another asset already has.
    public void checkNewId(AssetRequestDTO requestDTO) throws Exception {
        // taken id: the manager types another one
        if (assetRepository.findById(requestDTO.getAssetID()) != null) {
            throw new Exception(String.format(Message.ASSET_EXISTS,
                    requestDTO.getAssetID().toUpperCase()));
        }
    }

    // Function 3: adds the asset to the collection and to asset.dat.
    public AssetResponseDTO createAsset(AssetRequestDTO requestDTO) throws Exception {
        checkNewId(requestDTO);
        Asset asset = new Asset(requestDTO.getAssetID().toUpperCase(), requestDTO.getName(),
                requestDTO.getColor(), requestDTO.getPrice(), requestDTO.getWeight(),
                requestDTO.getQuantity());
        assetRepository.add(asset);
        return toResponse(asset);
    }

    // Function 4, first step: the asset with this id, or "Asset does not exist".
    public AssetResponseDTO findAsset(AssetRequestDTO requestDTO) throws Exception {
        return toResponse(requireAsset(requestDTO.getAssetID()));
    }

    // Function 4: copies the typed fields (null = keep; the id never changes) and saves.
    public AssetResponseDTO updateAsset(AssetRequestDTO requestDTO) throws Exception {
        Asset asset = requireAsset(requestDTO.getAssetID());
        // a new name was typed
        if (requestDTO.getName() != null) {
            asset.setName(requestDTO.getName());
        }
        // a new color was typed
        if (requestDTO.getColor() != null) {
            asset.setColor(requestDTO.getColor());
        }
        // a new price was typed
        if (requestDTO.getPrice() != null) {
            asset.setPrice(requestDTO.getPrice());
        }
        // a new weight was typed
        if (requestDTO.getWeight() != null) {
            asset.setWeight(requestDTO.getWeight());
        }
        // a new quantity was typed
        if (requestDTO.getQuantity() != null) {
            asset.setQuantity(requestDTO.getQuantity());
        }
        assetRepository.update(asset);
        return toResponse(asset);
    }

    // Returns the asset with this id, or throws the brief's message.
    private Asset requireAsset(String assetID) throws Exception {
        Asset asset = assetRepository.findById(assetID);
        // no asset has this id
        if (asset == null) {
            throw new Exception(Message.ASSET_NOT_EXIST);
        }
        return asset;
    }

    // Copies an asset into a row for the view.
    private AssetResponseDTO toResponse(Asset asset) {
        AssetResponseDTO row = new AssetResponseDTO();
        row.setAssetID(asset.getAssetID());
        row.setName(asset.getName());
        row.setColor(asset.getColor());
        row.setPrice(asset.getPrice());
        row.setWeight(asset.getWeight());
        row.setQuantity(asset.getQuantity());
        return row;
    }
}
