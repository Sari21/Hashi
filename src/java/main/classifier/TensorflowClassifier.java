package main.classifier;


import main.models.Level;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.FloatNdArray;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.StdArrays;
import org.tensorflow.types.TFloat32;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TensorflowClassifier {
    private static SavedModelBundle model;

    public static void loadModel() {
        String modelPath = "C:\\work\\onlab\\saved_model\\tensorflow_model_3";
        model = SavedModelBundle.load(modelPath, "serve");
//        System.out.println(model.session());
//        System.out.println(model.graph());
    }

    public static Level predict(float[] features) {
        if (model == null) {
            loadModel();
        }
        FloatNdArray input_matrix = NdArrays.ofFloats(Shape.of(1, 34));
        input_matrix.set(NdArrays.vectorOf(features), 0);
        Tensor input_tensor = TFloat32.tensorOf(input_matrix);
//        SignatureDef a = model.metaGraphDef().getSignatureDefMap().get("serving_default");
//        System.out.println(a);

        Map<String, Tensor> feed_dict = new HashMap<>();
        feed_dict.put("context", input_tensor);

        List<Tensor> output = model.session()
                .runner()
                .feed("serving_default_dense_40_input:0", input_tensor)
                .fetch("StatefulPartitionedCall:0")
//                .fetch("StatefulPartitionedCall:0")
                .run();
        float[][] res;
        try (TFloat32 out = (TFloat32) output.get(0)) {
            res = StdArrays.array2dCopyOf(out);
        }

//        {0: 'EASY', 1: 'HARD', 2: 'MEDIUM'}
        if (res[0][0] < res[0][1]) {
            if (res[0][1] < res[0][2])
                return Level.MEDIUM;
            else
                return Level.HARD;
        } else if (res[0][0] < res[0][2])
            return Level.MEDIUM;
        else
            return Level.EASY;
    }

    public static void closeSession() {
        model.session().close();
    }

}
