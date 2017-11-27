export default (state, props) => {
    const currentUser = state.currentUser;
    const shoppingCart = state.shoppingCart;
    
    if (!currentUser) {
      return { currentUser: null }
    }

    if(!shoppingCart){
      return { shoppingCart: null }
    }
  
    return {
      currentUser,
      shoppingCart,
    };
  }
  