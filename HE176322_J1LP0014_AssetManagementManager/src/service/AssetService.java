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
 * SERVICE: Functions 2, 3 and 4 - search, create and update assets.
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

    // Function 3, while the id is typed: refuses an id another asset already has.
    public void checkNewId(AssetRequestDTO requestDTO) throws Exception {
        // taken id: the manager types another one
        if (assetRepository.findById(requestDTO.getAssetId()) != null) {
            throw new Exception(String.format(Message.ASSET_EXISTS,
                    requestDTO.getAssetId().toUpperCase()));
        }
    }

    // Function 3: adds the asset to the collection and to asset.dat; the new asset as a
    // one-row table.
    public ArrayList<AssetDTO> createAsset(AssetRequestDTO requestDTO) throws Exception {
        ArrayList<AssetDTO> assetDTOList = new ArrayList<>();
        Asset asset = new Asset(requestDTO.getAssetId().toUpperCase(), requestDTO.getName(),
                requestDTO.getColor(), requestDTO.getPrice(), requestDTO.getWeight(),
                requestDTO.getQuantity());

        // the id is checked again (never trust the caller), then stored
        checkNewId(requestDTO);
        assetRepository.add(asset);
        assetDTOList.add(toAssetDTO(asset));
        return assetDTOList;
    }

    // Function 4, right after the id is typed: "Asset does not exist" when no asset has it.
    public void checkAssetExist(AssetRequestDTO requestDTO) throws Exception {
        findAsset(requestDTO.getAssetId());
    }

    // Function 4: copies the typed fields (null = keep; the id never changes) and saves;
    // the asset as a one-row table.
    public ArrayList<AssetDTO> updateAsset(AssetRequestDTO requestDTO) throws Exception {
        ArrayList<AssetDTO> assetDTOList = new ArrayList<>();
        Asset asset = findAsset(requestDTO.getAssetId());

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

        // write asset.dat, then hand back the row to show
        assetRepository.update(asset);
        assetDTOList.add(toAssetDTO(asset));
        return assetDTOList;
    }

    // Returns the asset with this id, or throws the brief's message.
    private Asset findAsset(String assetId) throws Exception {
        Asset asset = assetRepository.findById(assetId);

        // no asset has this id
        if (asset == null) {
            throw new Exception(Message.ASSET_NOT_EXIST);
        }

        return asset;
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
