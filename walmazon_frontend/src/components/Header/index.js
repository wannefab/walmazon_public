import React, { Component } from 'react';
import { connect } from 'react-redux';

import AppBar from 'material-ui/AppBar';

import './index.css';
import mapStateToProps from './mapStateToProps';
import ShoppingCart from './../ShoppingCart';
import ProductCategorySelector from './../ProductCategorySelector';
import SearchBar from './../SearchBar';


class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {
            filter: {type:'search',value:''},
            searchError: null,
        };
    }

      
    //--------------------------------------------------------------------------------------------------------------
    //----- SEARCH BAR ---------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------
    
    handleSearchbarChange = (event, value) => {
        this.setState({
            filter:  {type:'search',value:value},
        });
    };

    //--------------------------------------------------------------------------------------------------------------
    //----- FILTER -------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------

    handleFilterChange = (event, value) => {
        this.setState({
            filter:  {type:'tag',value:value},
        }, () => {
            this.props.handleFilter(this.state.filter)
        });
    };

    //--------------------------------------------------------------------------------------------------------------
    //----- RENDER -----------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------

    render() {
        return (
            <div className="appHeader">
                <AppBar
                    titleStyle={{textAlign:'center',fontSize:'3vw'}}
                    title={
                        <SearchBar 
                            handleSearchbarChange={this.handleSearchbarChange} 
                            handleFilter={this.props.handleFilter} 
                            filter={this.state.filter}  
                        /> 
                    }
                    iconElementLeft={
                        <ProductCategorySelector 
                            handleFilterChange={this.handleFilterChange} 
                            filter={this.state.filter}
                        /> 
                    }
                    iconElementRight= { 
                        <ShoppingCart 
                            windowWidth={this.props.windowWidth} 
                            itemsInCart={this.props.itemsInCart}
                            handleRemoveProductFromCart={this.props.handleRemoveProductFromCart}
                        /> 
                    } 
                />
            </div>
        )
    }
}

export default connect(mapStateToProps)(Header);