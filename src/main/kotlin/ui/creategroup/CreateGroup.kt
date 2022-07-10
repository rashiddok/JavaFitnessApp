package ui.creategroup

import androidx.compose.runtime.Composable
import javax.inject.Inject

class CreateGroup @Inject constructor(
    private val view: View,
    private val state: State,
    private val viewController: ViewController

        ) {

    init {
        view.init(state, viewController)
        viewController.init(state)
    }



    @Composable
    fun show() {
        view.show()
    }
}