package ru.murza.foodmodel.enums;

public enum BasketStatus {

    // ЗАКАЗ ОПЛАЧЕН И ЖДЕТ ЧТОБЫ ПОВАР ЕГО ВЗЯЛ СЕБЕ
    PAID,

    // ЗАКАЗ ЗАНЯТ И ГОТОВИТСЯ КАКИМ-ТО ПОВАРОМ
    ENGAGED,

    // ЗАКАЗ ПРИГОТОВЛЕН
    DONE

}
