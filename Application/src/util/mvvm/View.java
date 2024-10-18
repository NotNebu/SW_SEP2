package util.mvvm;

// Defines behavior for views following the MVVM pattern.
public interface View<V extends ViewModel<?>> {
    // Sets the associated ViewModel for the View.
    void setViewModel(V viewModel);

    // Initialize the view's components.
    void initialize();
}
