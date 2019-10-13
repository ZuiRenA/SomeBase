package project.shen.factor

/**
 * created by shen on 2019/10/7
 * at 23:30
 **/
@DialogMaker
class DialogBuilder {

}

@DialogMaker
internal abstract class IItemHolder {
    var callBack: ()-> Unit = {}
}

@DslMarker
internal annotation class DialogMaker