export default (state, props) => {
    const currentUser = state.currentUser;
    const products = state.products;
    
    if (!currentUser) {
      return { currentUser: null }
    }

    if (!products) {
      return { products: null, currentUser: null }
    }
  
    return {
      currentUser,
      products,
    };
  }
  