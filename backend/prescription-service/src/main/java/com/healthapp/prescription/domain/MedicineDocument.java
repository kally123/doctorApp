package com.healthapp.prescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.util.List;

/**
 * Elasticsearch document for medicine search.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "medicines")
@Setting(settingPath = "elasticsearch/medicine-settings.json")
public class MedicineDocument {

    @Id
    private String medicineId;
    
    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "standard")
    private String brandName;
    
    @Field(type = FieldType.Text, analyzer = "autocomplete")
    private String genericName;
    
    @Field(type = FieldType.Keyword)
    private String manufacturer;
    
    @Field(type = FieldType.Keyword)
    private String category;
    
    @Field(type = FieldType.Keyword)
    private String formulation;
    
    @Field(type = FieldType.Keyword)
    private String strength;
    
    @Field(type = FieldType.Integer)
    private Integer packSize;
    
    @Field(type = FieldType.Double)
    private BigDecimal price;
    
    @Field(type = FieldType.Boolean)
    private Boolean requiresPrescription;
    
    @Field(type = FieldType.Boolean)
    private Boolean isAvailable;
    
    @Field(type = FieldType.Keyword)
    private List<String> alternativeIds;
    
    @Field(type = FieldType.Keyword)
    private List<String> tags;
}
