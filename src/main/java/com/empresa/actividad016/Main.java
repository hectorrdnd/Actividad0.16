import com.mongodb.client.*;
import org.bson.Document;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String MONGO_URI = "mongodb+srv://lector:lector@cluster0.lxijdu9.mongodb.net/";
    private static final String DATABASE_NAME = "ejemplo";
    private static final String COLLECTION_NAME = "productos";

    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView<Document> tableView = new TableView<>();
        ObservableList<Document> data = FXCollections.observableArrayList();

        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            FindIterable<Document> documents = collection.find();
            for (Document document : documents) {
                data.add(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String key : data.get(0).keySet()) {
            TableColumn<Document, String> column = new TableColumn<>(key);
            column.setCellValueFactory(param -> {
                Object value = param.getValue().get(key);
                return value == null ? null : value.toString();
            });
            tableView.getColumns().add(column);
        }

        tableView.setItems(data);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MongoDB JavaFX Table");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
