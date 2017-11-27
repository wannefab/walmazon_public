import React, { Component } from 'react';
import { connect } from 'react-redux';

import {GridTile} from 'material-ui/GridList';
import IconButton from 'material-ui/IconButton';
import AddShoppingCartIcon from 'material-ui/svg-icons/action/add-shopping-cart';

import './index.css';
import mapStateToProps from './mapStateToProps';
import Header from './../Header';
import { fetchProducts, dumpProductCache, fetchNextProducts, addProductToCart, alterProductInCartAction } from '../../store/actions';

class Application extends Component {

  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      windowWidth: window.innerWidth,
      productFilter:  {type:'search',value:''},
      filterApplied: false,
      itemsInCart: 0,
    };
  }

  componentDidMount() {
    window.addEventListener('scroll', this.handleScroll);
    window.addEventListener('resize', this.handleResize, true);
  }

  handleScroll = () => {
    //Dynamically load more Products on scroll event
    if(this.props.products.length !== 0){
      if(Math.floor(window.scrollY) >= (( this.props.products.length/(Math.floor(this.state.windowWidth*0.9/300)) )*200 - 3000)  && !this.state.loading){
        this.setState({
          loading: true,
        });
        let numberOfItemsToFetch = 20*(Math.floor(window.innerWidth*0.9/300));

        this.props.dispatch(fetchNextProducts(numberOfItemsToFetch,this.state.productFilter))
        .then(()=>{
          this.setState({
            loading: false,
          });
        });
        
      }
    }
  }

  handleResize = () => {
    this.setState({
      windowWidth: window.innerWidth,
    });

  }

  handleAddProductToCart = (product, quantity) => {
    this.props.dispatch(addProductToCart(product,quantity))
    this.setState({
      itemsInCart: this.state.itemsInCart+quantity ,
    });
  }

  handleRemoveProductFromCart = (cartItem) => {
    this.setState({
      itemsInCart: this.state.itemsInCart-cartItem.quantity ,
    });
    cartItem.quantity=0;
    this.props.dispatch(alterProductInCartAction(cartItem))
  }

  handleFilter = (filter) => {
    this.setState({
      filterApplied: false,
      productFilter: filter,
    }, () => {
      this.props.dispatch(dumpProductCache());
    });
  }

  renderProductTiles(){
    let tiles = [];
    let numberOfItemsToFetch = 20*(Math.floor(window.innerWidth*0.9/300));

    if(this.props.products.length===0 && !this.state.filterApplied ){
      this.props.dispatch(fetchProducts(numberOfItemsToFetch,this.state.productFilter))
      .then(()=>{
        this.setState({
          filterApplied: true,
        });
      });
    }
    else{
      for(let i = tiles.length; i< this.props.products.length ; i++){
        tiles.push(
          <div key={this.props.products[i].id} className="Tile">
            <GridTile
              titleBackground='rgba(0, 0, 0, 0.6)'
              title={this.props.products[i].name +" - "+this.props.products[i].price +"CHF"}
              subtitle={<span><b>{this.props.products[i].description}</b></span>}
              actionIcon={<IconButton onClick={() => this.handleAddProductToCart(this.props.products[i],1)}><AddShoppingCartIcon color="white" /></IconButton>}
            >
              <img src={this.props.products[i].images} alt={"Loading..."}/>
            </GridTile>
          </div>
        )
      };
    }
    return tiles;
  }


  render() {    
    return (
        <div>
          <Header 
            windowWidth={this.state.windowWidth} 
            handleFilter={this.handleFilter} 
            itemsInCart={this.state.itemsInCart} 
            handleRemoveProductFromCart={this.handleRemoveProductFromCart} 
          />
            <div className="TileContainer">
              {this.renderProductTiles()}
            </div>
        </div>
    );
  }

}
  
export default connect(mapStateToProps)(Application);
  

