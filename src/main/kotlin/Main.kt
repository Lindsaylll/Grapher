import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.random.Random


class Main : Application() {



    val model = Model()
    // this is the main interface
    val main = BorderPane()
    override fun start(stage: Stage) {
        // create all given datasets: "increasing", "large"...., and store the datasets into a list waiting for future modification
        model.add_all()
        // window name
        stage.title = "A2 Grapher (x645liu)"
        // menu bar: "ToolBarView" and interface area for editing the dataset title and axes: "editView"
        main.top = VBox(ToolBarView(model, this), editView(model, this))
        // status bar
        main.bottom = StatusView(model)
        // data table
        val lft = ScrollPane(tableView(model, this))
        lft.isFitToWidth = true
        main.left = lft
        // data statistics containing sum, max, ave...
        main.right = statView(model, this)
        // the bar graph part together with its y-axis, x-axis...
        main.center = interfaceView(model, this)
        // create and config the window
        val scene = Scene(main, 800.0, 600.0)
        stage.scene = scene
        stage.minWidth = 600.0
        stage.minHeight = 400.0
        // start it all up
        stage.show()
    }

    // switch between pie chart and bar graph
    fun swichScene(showPie: Boolean){
        if(showPie == true)  {main.center = interfaceView2(model, this)}
        debug("$showPie")
        if(showPie == false)  {main.center = interfaceView(model, this)}
    }

    // show the new chosen dataset
    fun changeDataSet(name: String) {
        // modify the variable that indicate the current dataset
        model.curData = name
        // ask model to notifyObservers
        model.updateText()
        // show the graph for the new chosen dataset
        if(model.showpiechart== false) main.center = interfaceView(model, this)
        if(model.showpiechart== true) main.center = interfaceView2(model, this)
        // show the statistics
        main.right = statView(model, this)
        // show the data table for the new chosen dataset
        val lft = ScrollPane(tableView(model, this))
        lft.setStyle("-fx-background-color:transparent;")
        lft.isFitToWidth = true
        main.left = lft
    }

    // add new datasets evoking by clicking button "New"
    fun newData(){
        // title is 1 to 3 words in Title Case
        val title = LoremIpsum.getRandomSequence(Random.nextInt(1, 3))
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
        // x-axis is 1 word in Title Case
        val xName = LoremIpsum.getRandomSequence(Random.nextInt(1, 2))
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
        // Y-axis is 1 word in Title Case
        val yName = LoremIpsum.getRandomSequence(Random.nextInt(1, 2))
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
        // to check the new dataset's number of data points
        val barNums = model.creatHowMany
        // to format the name of the new dataset, it should be newX where  X is a unique integer
        val newName = "New" + model.countX.toString()
        // increment X, so the X is a new unique integer now
        model.countX += 1
        // I modified LoremIpsum file and added a new method call getRandomData(num: Int)
        // given num, getRandomData(num: Int) will create a random mutableListOf<Int>()
        val datavalues = LoremIpsum.getRandomData(barNums)
        // creating new dataset
        val newData = DataSet(title,xName,yName,datavalues)
        // change the indicator of the current dataset we are showing
        model.curData = newName
        // save the new created data to data list
        saveData(newData,newName)
    }

    // save dataset
    fun saveData(data: DataSet?, newName:String) {
        if (data != null) {
            model.add_data(data , newName)
        }
    }
}