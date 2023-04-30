package mvc_components

object Demo {
    @main def main(): Unit = {
        val model = new Model(true)
        val view = new View(model)
        val controller = new Controller(view,model)

        view.init(controller, model)
    }
}
