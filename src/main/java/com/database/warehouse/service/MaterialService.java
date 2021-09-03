package com.database.warehouse.service;

import com.database.warehouse.exception.DeleteUnable;
import com.database.warehouse.entity.Material;
import com.database.warehouse.entity.MaterialStoration;
import com.database.warehouse.entity.ProductMaterial;
import com.database.warehouse.exception.MaterialExist;
import com.database.warehouse.exception.MaterialNotFound;
import com.database.warehouse.mapper.HistoryRecordMapper;
import com.database.warehouse.mapper.MaterialMapper;
import com.database.warehouse.mapper.MaterialStorationMapper;
import com.database.warehouse.mapper.ProductMaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private ProductMaterialMapper productMaterialMapper;
    @Autowired
    private MaterialStorationMapper materialStorationMapper;
    @Autowired
    private HistoryRecordMapper historyRecordMapper;

    public List<Material> findMaterials() {
        return materialMapper.selectMaterials();
    }

    public List<Material> findMaterialByWid(Long wid) {
        return materialMapper.selectMaterialByWid(wid);
    }

    public Material findMaterialByMid(Long mid) throws MaterialNotFound {
        Material material = materialMapper.selectValidMaterialByMid(mid);
        if (material == null) {
            throw new MaterialNotFound();
        }
        return material;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Long addMaterial(Material material) throws MaterialExist {
        Long mid = materialMapper.selectValidMidByName(material.getName());
        if (mid != null) {
            throw new MaterialExist();
        }
        mid = materialMapper.selectMidByName(material.getName());
        if (mid != null) {
            material.setMid(mid);
            materialMapper.activateMaterial(material);
            return mid;
        } else {
            materialMapper.insertMaterial(material);
            return material.getMid();
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateMaterial(Material material) throws MaterialExist {
        String name = material.getName();
        Long mid = materialMapper.selectMidByName(name);
        if (mid != null) {
            throw new MaterialExist();
        }
        materialMapper.updateMaterial(material);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void removeMaterial(Long mid) throws DeleteUnable {
        List<MaterialStoration> materialStorations =
                materialStorationMapper.selectMaterialStorationByMid(mid);
        if (materialStorations.size() != 0) {
            throw new DeleteUnable();
        }
        materialMapper.deleteMaterial(mid);
    }

}
