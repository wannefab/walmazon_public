import { combineReducers } from 'redux';

import {  START_SESSION, 
          END_SESSION, 
          LOAD_PRODUCTS, 
          DUMP_PRODUCT_CACHE, 
          ADD_NEXT_PRODUCTS, 
          ADD_PRODUCT_TO_CART,
          ALTER_PRODUCT_QUANTITY_IN_CART, 
          CLEAR_CART,
          LOAD_ORDERS
        } from './actions';


const currentUser = (state = {}, action) => {
    switch (action.type) {
      case START_SESSION: {
        const newState = action.payload;
        return newState;
      }
      case END_SESSION: {
        const newState = {};
        return newState;
      }
      default: {
        return state;
      }
    }
  }

  
const products = (state = [], action) => {
    switch (action.type) {
      case LOAD_PRODUCTS: {
        const newState = action.payload;
        return newState;
      }
      case ADD_NEXT_PRODUCTS: {
        const newState = [...state]
        action.payload.forEach(function(element) {
          newState.push(element)
        }, this);
        return newState;
      }
      case DUMP_PRODUCT_CACHE: {
        const newState = [];
        return newState;
      }
      /*
      case END_SESSION: {
        const newState = [];
        return newState;
      }*/
      default: {
        return state;
      }
    }
  }

  const shoppingCart = (state = [], action) => {
    switch (action.type) {
      case ADD_PRODUCT_TO_CART: {
        const newState = [...state]

        let index = newState.findIndex((element) =>{
            return element.product.id === action.payload.product.id;
          }
        )

        if(index === -1){
          newState.push(action.payload)
        }
        else{
          newState[index].quantity += 1;
        }

        return newState;
      }
      case ALTER_PRODUCT_QUANTITY_IN_CART: {
        const newState = [...state]

        let index = newState.findIndex((element) =>{
            return element.product.id === action.payload.product.id;
          }
        )

        if(index>-1){
          if(action.payload.quantity === 0){
            newState.splice(index, 1);
          }
          else{
            newState[index].quantity=action.payload.quantity;
          }
        }
        
        return newState;
      }
      case CLEAR_CART: {
        const newState = []
        return newState;
      }
      default: {
        return state;
      }
    }
  }

  const orders = (state = [], action) => {
    switch (action.type) {
      case LOAD_ORDERS: {
        const newState = action.payload;
        return newState;
      }
      default: {
        return state;
      }
    }
  }
  

export default combineReducers({
  currentUser,
  products,
  shoppingCart,
  orders,
});
  