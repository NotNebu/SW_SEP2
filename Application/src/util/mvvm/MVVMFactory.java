package util.mvvm;

// Factory class for creating MVVM components.
public class MVVMFactory<M extends Model, VM extends ViewModel<M>, V extends View<VM>> {

    // Creates a new View with its associated ViewModel and Model using reflection.
    public V createView(Class<M> modelClass, Class<VM> viewModelClass, Class<V> viewClass) {
        try {
            M model = modelClass.getDeclaredConstructor().newInstance();
            VM viewModel = viewModelClass.getDeclaredConstructor(modelClass).newInstance(model);
            V view = viewClass.getDeclaredConstructor(viewModelClass).newInstance(viewModel);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Creates a new View, using an existing Model instance.
    public V createView(M existingModel, Class<VM> viewModelClass, Class<V> viewClass) {
        try {
            VM viewModel = viewModelClass.getDeclaredConstructor(existingModel.getClass()).newInstance(existingModel);
            V view = viewClass.getDeclaredConstructor(viewModelClass).newInstance(viewModel);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
