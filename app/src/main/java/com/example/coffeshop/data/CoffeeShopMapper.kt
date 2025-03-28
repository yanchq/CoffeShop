package com.example.coffeshop.data

import com.example.coffeshop.domain.entity.Client
import javax.inject.Inject

class CoffeeShopMapper @Inject constructor() {

    fun clientDbModelToEntity(clientDbModel: ClientDbModel): Client {
        return Client(
            id = clientDbModel.id,
            name = clientDbModel.name,
            email = clientDbModel.email
        )
    }

    fun clientEntityToDbModel(client: Client): ClientDbModel {
        return ClientDbModel(
            id = client.id,
            name = client.name,
            email = client.email
        )
    }
}