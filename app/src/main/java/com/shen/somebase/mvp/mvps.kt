package com.shen.somebase.mvp

interface IMVP {

    interface IPresenter<out V: IView<IPresenter<V>>>: Ilifeycycle {
        //kotlin中接口里可以有变量, 也可以有方法，类似于jdk 1.8以后的default
        val view: V
    }

    interface IView<out P: IPresenter<IView<P>>>: Ilifeycycle, BaseView {
        val presenter: P
    }
}