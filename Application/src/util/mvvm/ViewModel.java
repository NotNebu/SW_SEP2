package util.mvvm;

// Defines the expected behavior of a ViewModel in the MVVM pattern.
public interface ViewModel<M extends Model> {
    // Sets the model to be used by the ViewModel.
    void setModel(M model);
}
