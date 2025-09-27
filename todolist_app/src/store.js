import { createStore } from 'redux';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // Use localStorage for web
import Reducer from './Reducer/Reducer';

// Persist configuration
const persistConfig = {
  key: 'root',
  storage, // Default: localStorage
};

// Enhanced reducer
const persistedReducer = persistReducer(persistConfig, Reducer);

// Create store with persisted reducer
const store = createStore(persistedReducer);

// Create the persistor
const persistor = persistStore(store);

export { store, persistor };
