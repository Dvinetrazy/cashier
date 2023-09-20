package com.smk.cashier.service;

import com.smk.cashier.model.Barang;
import com.smk.cashier.service.BarangService;
import junit.framework.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

class BarangServiceTest {

    @org.junit.jupiter.api.Test
    @Order(1)
    void addBarang() {
        Barang laptop = new Barang();
        laptop.setKodeBarang("LP001");
        laptop.setNamaBarang("Laptop");
        laptop.setHargaBarang(5000000);
        BarangService.getInstance()
                .addBarang(laptop);

        Barang mouse = new Barang();
        mouse.setKodeBarang("MO001");
        mouse.setNamaBarang("Mouse");
        mouse.setHargaBarang(500000);
        BarangService.getInstance()
                .addBarang(mouse);
    }
    @Test
    @Order(2)
    void getBarangList() {
        List<Barang> barangList
                = BarangService
                .getInstance()
                .getBarangList();
        Assert.assertEquals(barangList.size(),2);
    }


}