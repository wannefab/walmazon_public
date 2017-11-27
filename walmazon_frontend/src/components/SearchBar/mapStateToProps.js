export default (state, props) => {
    const currentUser = state.currentUser;
    
    if (!currentUser) {
      return { currentUser: null }
    }

    return {
      currentUser,
    };
  }
  