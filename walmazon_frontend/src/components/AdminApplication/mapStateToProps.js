export default (state, props) => {
    const currentUser = state.currentUser;
    const orders = state.orders;
    
    if (!currentUser) {
      return { currentUser: null }
    }

    if (!orders) {
      return { orders: null, currentUser: null }
    }
  
    return {
      currentUser,
      orders,
    };
  }
  