package main;


import javafx.application.Application;
import javafx.stage.Stage;
import main.classifier.TensorflowClassifier;
import main.controllers.MenuController;
import main.models.Board;
import main.view.BoardView;

public class Main extends Application {

       static Board board;
       private static BoardView boardView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuController.showMenuStage();
//        Board board = new Board();
//        board.addIsland(new Island(new Coordinates(1,1), 1, 2));
//        board.addIsland(new Island(new Coordinates(3,1), 2, 3));
//        board.addIsland(new Island(new Coordinates(2,4), 3, 3));
//        board.addIsland(new Island(new Coordinates(4,1), 4, 4));
//        board.addIsland(new Island(new Coordinates(4,4), 5, 1));
//        board.addIsland(new Island(new Coordinates(5,3), 6, 1));
//        Solver.solve(board);
    }


    public static void main(String[] args) {
        launch(args);
    }



//
//        public static void main(String[] args) throws Exception {
//            System.out.println("Hello TensorFlow " + TensorFlow.version());
//
//            try (ConcreteFunction dbl = ConcreteFunction.create(Main::dbl);
//                 TInt32 x = TInt32.scalarOf(10);
//                 Tensor dblX = dbl.call(x)) {
//                System.out.println(x.getInt() + " doubled is " + ((TInt32)dblX).getInt());
//            }
////            TensorflowClassifier tfc = new TensorflowClassifier();
////            tfc.loadModel();
//        }
//
//        private static Signature dbl(Ops tf) {
//            Placeholder<TInt32> x = tf.placeholder(TInt32.class);
//            Add<TInt32> dblX = tf.math.add(x, x);
//            return Signature.builder().input("x", x).output("dbl", dblX).build();
//        }


}
