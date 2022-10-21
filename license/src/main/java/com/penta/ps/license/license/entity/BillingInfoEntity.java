package com.penta.ps.license.license.entity;

import com.penta.ps.license.license.type.TypeDefine;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "billing_info", schema = "billing", catalog = "billing")
public class BillingInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_name", length = 64)
    private String componentName;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "billing_type", columnDefinition = "smallint")
    private TypeDefine.BillingType billingType;

    @Column(name = "core", columnDefinition = "smallint")
    private int core;

    @Column(name = "hourly_price", columnDefinition = "int")
    private Integer hourlyPrice;

    @Column(name = "base_price", columnDefinition = "int")
    private Integer basePrice;

    @Builder
    public BillingInfoEntity(String componentName,
                             TypeDefine.BillingType billingType,
                             int core,
                             Integer hourlyPrice,
                             Integer basePrice) {
        this.componentName = componentName;
        this.billingType = billingType;
        this.core = core;
        this.hourlyPrice = hourlyPrice;
        this.basePrice = basePrice;
    }
}
