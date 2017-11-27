const ip = 'http://localhost:8090'

export const START_SESSION = 'startSession';
export const END_SESSION = 'endSession';

export const LOAD_PRODUCTS = 'loadProducts';
export const ADD_NEXT_PRODUCTS = 'addNextProducts';
export const DUMP_PRODUCT_CACHE = 'dumpProductCacheAction';

export const ADD_PRODUCT_TO_CART = 'addProductToCartAction';
export const ALTER_PRODUCT_QUANTITY_IN_CART = 'deleteProductFromCartAction';
export const CLEAR_CART = 'clearCart';
export const PERFORM_PURCHASE = 'performPurchase';

export const LOAD_ORDERS = 'loadOrders';


//--------------------------------------------------------------------------------------------------------------
//----- LOGIN / LOGOUT / REGISTRATION --------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

export const startSession = (userData) => ({
    type: START_SESSION,
    payload: userData,
});

export const loginUser = (email,password) => (dispatch, getState) => {
    var payload = {
        email: email,
        password: password,
    };
    return fetch(ip+"/users/sign_in",
        {
            method: "POST",
            headers: {
            'Content-Type': 'application/json'
            },
            body: JSON.stringify( payload )
        })
        .then(function(res){ return res.json(); })
        .then(data => {
            const action = startSession(data);
            dispatch(action);
            return data;
        });
}

export const loginAdmin = (email,password) => (dispatch, getState) => {
    var payload = {
        email: email,
        password: password,
    };
    return fetch(ip+"/users/admin/sign_in",
        {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify( payload )
        })
        .then(function(res){ return res.json(); })
        .then(data => {
            const action = startSession(data);
            dispatch(action);
            return data;
        });
}

export const registerUser = (title, firstName, lastName, address, city, birthDate, username, email, password) => (dispatch, getState) => {
    var payload = {
        title: title,
        firstName: firstName,
        lastName: lastName,
        address: address,
        city: city,
        birthDate: birthDate,
        username: username,
        email: email,
        password: password,
    };
    return fetch(ip+"/users/sign_up",
        {
            method: "POST",
            headers: {
            'Content-Type': 'application/json'
            },
            body: JSON.stringify( payload )
        })
}

export const endSession = () => ({
    type: END_SESSION,
});

export const logoutUser = () => (dispatch, getState) => {
    const action = endSession();
    dispatch(action);
}


//--------------------------------------------------------------------------------------------------------------
//----- PRODUCTS -----------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

export const dumpProductCacheAction = () => ({
    type: DUMP_PRODUCT_CACHE,
});

export const dumpProductCache = () => (dispatch, getState) => {
    const action = dumpProductCacheAction();
    dispatch(action);
}

export const loadProducts = (productData) => ({
    type: LOAD_PRODUCTS,
    payload: productData,
});

export const fetchProducts = (number,filter) => (dispatch, getState) => {
    let fetchAddress = '';
    if(filter.type === 'tag' && filter.value !== ''){
        fetchAddress = ip+"/products/tags/0/"+number+"/"+filter.value;
    }
    else{
        fetchAddress = ip+"/products/0/"+number+"/"+filter.value;
    }
    
    return fetch(fetchAddress,
    {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(function(res){ return res.json(); })
    .then(data => {
        const action = loadProducts(data);
        dispatch(action);
    });
}

export const addNextProducts = (productData) => ({
    type: ADD_NEXT_PRODUCTS,
    payload: productData,
});

export const fetchNextProducts = (number,filter) => (dispatch, getState) => {
    let products = getState().products;
    let fetchAddress = '';
    if(filter.type === 'tag' && filter.value !== ''){
        fetchAddress = ip+"/products/tags/"+products[products.length-1].id+"/"+number+"/"+filter.value;
    }
    else{
        fetchAddress = ip+"/products/"+products[products.length-1].id+"/"+number+"/"+filter.value;
    }

    return fetch(fetchAddress,
    {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(function(res){ return res.json(); })
    .then(data => {
        const action = addNextProducts(data);
        dispatch(action);
    });
}


//--------------------------------------------------------------------------------------------------------------
//----- SHOPPING CART ------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

export const addProductToCart = (product, quantity) => (dispatch, getState) => {
    const action = addProductToCartAction({product: product, quantity: quantity});
    dispatch(action);
};

export const addProductToCartAction = (productData) => ({
    type: ADD_PRODUCT_TO_CART,
    payload: productData,
});


export const alterProductInCart = (product) => (dispatch, getState) => {
    const action = alterProductInCartAction(product);
    dispatch(action);
};

export const alterProductInCartAction = (productData) => ({
    type: ALTER_PRODUCT_QUANTITY_IN_CART,
    payload: productData,
});


export const clearCart = () => (dispatch, getState) => {
    const action = {type: CLEAR_CART};
    dispatch(action);
};


//--------------------------------------------------------------------------------------------------------------
//----- PURCHASE -----------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

export const performPurchase = (data) => ({
    type: PERFORM_PURCHASE,
    payload: data,
});

export const purchaseProducts = (product) => (dispatch, getState) => {
    let token = "Bearer "+getState().currentUser.token;
    let shoppingCart = getState().shoppingCart;

    shoppingCart.forEach(function(product) {
        var payload = {
            status: 'open',
            quantity: product.quantity,
        };
        return fetch(ip+"/purchases/"+product.product.id,
        {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization':token
            },
            body: JSON.stringify( payload )
        })
    }, this);
    dispatch(clearCart());
}


//--------------------------------------------------------------------------------------------------------------
//----- ORDERS -------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------

export const loadOrders = (orderData) => ({
    type: LOAD_ORDERS,
    payload: orderData,
});

export const fetchOrders = (number,filter) => (dispatch, getState) => {
    let token = "Bearer "+getState().currentUser.token;
    
    return fetch(ip+"/purchases/",
    {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization':token,
        },
    })
    .then(function(res){ return res.json(); })
    .then(data => {
        console.log(data)
        const action = loadOrders(data);
        dispatch(action);
    });
}