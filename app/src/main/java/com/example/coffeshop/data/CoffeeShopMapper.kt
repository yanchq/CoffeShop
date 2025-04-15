package com.example.coffeshop.data

import com.example.coffeshop.domain.entity.Item
import javax.inject.Inject

class CoffeeShopMapper @Inject constructor() {

    fun itemToDbModel(item: Item): OrderItemDbModel {
        return OrderItemDbModel(
            id = item.id,
            name = item.name,
            cost = item.cost,
            category = item.category,
            size = item.size,
            sugar = item.sugar,
            syrup = item.syrup,
            image = item.image
        )
    }

    fun dbModelToItem(dbModel: OrderItemDbModel): Item {
        return Item(
            id = dbModel.id,
            name = dbModel.name,
            cost = dbModel.cost,
            category = dbModel.category,
            size = dbModel.size,
            sugar = dbModel.sugar,
            syrup = dbModel.syrup,
            image = dbModel.image
        )
    }

    fun listDbModelToListItem(listDbModel: List<OrderItemDbModel>): List<Item> {
        val list = mutableListOf<Item>()

        listDbModel.map {
            list.add(dbModelToItem(it))
        }
        return list
    }
}