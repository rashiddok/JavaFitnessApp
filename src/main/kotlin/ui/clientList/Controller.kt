package ui.clientList

import entity.Client
import ui.createClient.NewClientDialog
import javax.inject.Inject
import javax.inject.Provider
import kotlin.streams.toList

class Controller @Inject constructor(
    private val newClientDialogProvider: Provider<NewClientDialog>
) {
    private lateinit var model: Model;

    private lateinit var fullClientList: List<Client>

    fun init(model: Model) {
        this.model = model

        fullClientList = listOf(
            Client("Dima", "Dima", "Dima"),
            Client("Vlad", "Vlad", "Vlad"),
            Client("Mia", "Mia", "Mia"),
            Client("Oly", "Oly", "Oly"),
            Client("Paul", "Paul", "Paul"),
            Client("Angelina", "Angelina", "Angelina")
        )

        model.clientList.value = fullClientList;
    }

    fun find(searchPattern: String) {
        model.searchPattern.value = searchPattern
        model.clientList.value = fullClientList.stream()
            .filter { client ->
                client.firstName.contains(searchPattern, true)
                        || client.lastName.contains(searchPattern, true)
                        || client.patronymic.contains(searchPattern, true)
            }
            .toList()
    }

    fun selectClient(client: Client) {
        model.selectedClient.value = client
    }

    fun createNewClient() {
        model.newClientDialog.value = newClientDialogProvider.get()
            .apply {
                init { model.newClientDialog.value = null }
            }
    }
}