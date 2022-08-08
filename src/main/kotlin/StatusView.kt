import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class StatusView(
    private val model: Model
): HBox(), IView {
    override fun updateView() {
        debug("DBG: updateviewSV")
        // showing new status
        count.text = model.count.toString()
    }

    val count = Label("6")
    val message = Label("datasets")

    init {
        // the background of status bar is LIGHTGREY
        background = Background(BackgroundFill(Color.LIGHTGREY, null, null))
        //layout
        padding = Insets(10.0)
        spacing = 4.0
        prefHeight = 30.0
        alignment = Pos.CENTER_LEFT
        minHeight = 30.0
        // add to pane
        children.addAll(count,message)
        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}