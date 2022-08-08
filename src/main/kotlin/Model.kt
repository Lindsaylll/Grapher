class Model {

    private val views: ArrayList<IView> = ArrayList()

    // method that the views can use to register themselves with the Model
    // once added, they are told to update and get state from the Model
    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }
    // the model uses this method to notify all of the Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
    private fun notifyObservers() {
        for (view in views) {
            view.updateView()
        }
    }
    // record all added/modified datasets
    var allDataSets = mutableListOf<DataSet>()
    // record each dataset's name together with its index in "allDataSets"
    var myDataList = mutableListOf<myDatas>()
    // the number of datasets
    var count = 6
    // the number of data points about to create for new dataset
    var creatHowMany = 5
    // the idx of added dataset
    var countX = 1
    // the name of the data showing on the scene
    var curData = "Increasing"
    // which graph is showing
    var showpiechart = false

    // create all given datasets: "increasing", "large"...., and store the datasets into a list waiting for future modification
    fun add_all(){
        // creating the 6 default datasets and add the default datasets to dataset list
        createTestDataSet("Increasing")?.let { allDataSets.add(it) }
        createTestDataSet("Large")?.let { allDataSets.add(it) }
        createTestDataSet("Middle")?.let { allDataSets.add(it) }
        createTestDataSet("Single")?.let { allDataSets.add(it) }
        createTestDataSet("Range")?.let { allDataSets.add(it) }
        createTestDataSet("Percentage")?.let { allDataSets.add(it) }
        // add the default datasets to "myDataList" indicate dataset's name together with its index
        myDataList.addAll(listOf(myDatas(0,"Increasing"),myDatas(1,"Large"),myDatas(2,"Middle"),
            myDatas(3,"Single"),myDatas(4,"Range"),myDatas(5,"Percentage")))
//        allDataSets.addAll(a1, a2, a3, a4,a5,a6)
    }


    // add new dataset
    fun add_data(data: DataSet, newName:String){
        allDataSets.add(data)
        // add the new dataset to a list that stores the dataset's name and its index in dataset list
        myDataList.add(myDatas(count,"$newName"))
        //
        count += 1
    }
    // find the index of a dataset with given name
    fun findSet(name:String):Int{
        var idx = 0
        for (item in myDataList){
            if(name == item.name){
                idx = item.idx
                break;
            }
            idx++;
        }
        return idx
    }
    // find the index of the showing dataset
    fun get_curDataIdx():Int{
        var dataidx = findSet(curData)
        return dataidx

    }

    // modify the dataset with given name and the index of the data point that is about to be modified and its new data
    fun modify(name:String, idx: Int, newVal:Int){
        var dataidx = findSet(name)
        debug("dataidx: $dataidx")
        debug("spinner idx: $idx")
        allDataSets[dataidx].data[idx] = newVal
        notifyObservers()
    }

    // updateView
    fun updateText(){
        notifyObservers()
    }

    // update the name for x-axis/ y-axis / title
    fun updateName(idx:Int, updatedThing:String,updatedName:String){
        when(updatedThing){
            "X" -> allDataSets[idx].xAxis = updatedName
            "Y" -> allDataSets[idx].yAxis = updatedName
            "title" -> allDataSets[idx].title = updatedName
            else -> null
        }
        notifyObservers()
    }

    // public way to get number of notes in the list
    val dataNum: Int
        get() = allDataSets.size
}