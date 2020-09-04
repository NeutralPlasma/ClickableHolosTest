package eu.virtusdevelops.clickableholostest.placeholder

import eu.virtusdevelops.clickableholostest.API.PlaceholderReplacer

class TimedPlaceholdersUpdater(private val frames: MutableList<String>) :
    PlaceholderReplacer {

    private var index : Int = 0


    override fun update(): String {
        val currentFrame = frames[index]

        if(index == frames.size - 1){
            index = 0
        }else{
            index++;
        }
        return currentFrame
    }
}