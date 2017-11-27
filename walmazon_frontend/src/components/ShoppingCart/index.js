import React, { Component } from 'react';
import { connect } from 'react-redux';

import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import MenuItem from 'material-ui/MenuItem';
import Badge from 'material-ui/Badge';
import IconMenu from 'material-ui/IconMenu';

import AccountIcon from 'material-ui/svg-icons/action/account-box';
import ShoppingCartIcon from 'material-ui/svg-icons/action/shopping-cart';
import SettingsIcon from 'material-ui/svg-icons/action/settings';
import DeleteItemIcon from 'material-ui/svg-icons/action/delete';

import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow,
    TableRowColumn,
  } from 'material-ui/Table';
import Drawer from 'material-ui/Drawer';

import mapStateToProps from './mapStateToProps';
import Login from './../Login';
import { logoutUser, purchaseProducts } from '../../store/actions';


var _colors = require('material-ui/styles/colors');
const shoppingCartEmpty = ['Didn\'t you forget something?','Your shopping cart looks soo empty...','Just keep adding Products to your cart','There\'s still room for more!'];
const shoppingCartFull = ['Here\'s your shopping cart','Didn\'t you forget something?','I\'m pround of you!','Glad you need my Products','You will be sooo happy','And they say you cannot buy happiness! HA!'];


class ShoppingCart extends Component {
    state = {
        open: false,
    };

    handleToggleShoppingDrawer = () => this.setState({open: !this.state.open});

    handleLogout = () => {
        this.props.dispatch(logoutUser());
    };

    handlPurchaseProducts = () => {
        this.props.dispatch(purchaseProducts(this.props.shoppingCart[0]));
    }

    render() {
        let total=0;

        return (
            <div>
                <Badge
                    badgeContent={this.props.itemsInCart}
                    secondary={true}
                    badgeStyle={{'backgroundColor':_colors.purple800,marginTop:4}}
                    >
                    <IconButton 
                        style={{marginTop:'-16px',marginRight:'-16px'}}
                        onClick={this.handleToggleShoppingDrawer}
                    >
                        <ShoppingCartIcon color="white"/>
                    </IconButton>
                </Badge>

                <Drawer
                    docked={false}
                    width={this.props.windowWidth*0.9}
                    openSecondary={true}
                    open={this.state.open}
                    onRequestChange={(open) => this.setState({open})}
                >
                    <AppBar
                        style={{'backgroundColor':_colors.purple800}}
                        titleStyle={{textAlign:'center',fontSize:'2.5vw'}}
                        title={
                            this.props.currentUser.token ?
                                this.props.shoppingCart.length>5 ? shoppingCartFull[Math.floor(Math.random()*shoppingCartFull.length)]: shoppingCartEmpty[Math.floor(Math.random()*shoppingCartEmpty.length)]
                            :
                                "Shopping Cart"
                            }
                        iconElementLeft={<div/>}
                        iconElementRight={
                            this.props.currentUser.token ?
                                <div>
                                    <IconButton tooltip="Settings" touch={true} tooltipPosition="bottom-left"><SettingsIcon color="white"/></IconButton>
                                    <IconButton onClick={this.handleLogout} tooltip="Logout" touch={true} tooltipPosition="bottom-left"><AccountIcon color="white"/></IconButton>
                                </div>
                            :
                                <IconMenu
                                    iconButtonElement={<IconButton tooltip="Login" touch={true} tooltipPosition="bottom-left"><AccountIcon color="white"/></IconButton>}
                                    targetOrigin={{horizontal: 'right', vertical: 'top'}}
                                    anchorOrigin={{horizontal: 'right', vertical: 'top'}}
                                >
                                    <Login/>
                                </IconMenu>
                            }
                    />
                    <Table onRowSelection={this.handleRowSelection}>
                        <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                            <TableRow>
                                <TableHeaderColumn style={{width:15,paddingRight:10,paddingLeft:5}}></TableHeaderColumn>
                                <TableHeaderColumn>Name</TableHeaderColumn>
                                <TableHeaderColumn style={{width:80,paddingRight:5,paddingLeft:5,textAlign:'center'}}>Quantity</TableHeaderColumn>{
                                    this.props.windowWidth < 500 ?
                                        <TableHeaderColumn width={1}></TableHeaderColumn>
                                    :
                                        <TableHeaderColumn style={{width:(this.props.windowWidth*0.2),paddingRight:5,paddingLeft:5,textAlign:'right'}}>Price per Unit</TableHeaderColumn>
                                    }
                                <TableHeaderColumn style={{width:(this.props.windowWidth*0.2),paddingRight:17,paddingLeft:5,textAlign:'right'}}>Price in CHF</TableHeaderColumn>
                            </TableRow>
                        </TableHeader>
                        <TableBody displayRowCheckbox={false}>
                            {this.props.shoppingCart.map((item, index) => (
                                <TableRow key={index} >
                                    <TableHeaderColumn style={{width:15,paddingRight:10,paddingLeft:5}}> <IconButton onClick={() => this.props.handleRemoveProductFromCart(item)}><DeleteItemIcon/></IconButton> </TableHeaderColumn>
                                    <TableRowColumn>{item.product.name}</TableRowColumn>
                                    <TableRowColumn style={{width:80,paddingRight:5,paddingLeft:5,textAlign:'center'}}> {item.quantity} </TableRowColumn>{
                                        this.props.windowWidth < 500 ?
                                            <TableHeaderColumn width={1}></TableHeaderColumn>
                                        :
                                            <TableRowColumn style={{width:(this.props.windowWidth*0.2),paddingRight:5,paddingLeft:5,textAlign:'right'}}>{item.product.price} CHF</TableRowColumn>
                                        }
                                    <TableRowColumn style={{width:(this.props.windowWidth*0.2),paddingRight:17,paddingLeft:5,textAlign:'right'}}>{Math.ceil(item.product.price*item.quantity*100)/100} CHF</TableRowColumn>
                                    {total = total + (item.product.price*item.quantity)}
                                </TableRow>
                            ))}         
                        </TableBody>
                    </Table>
                    <MenuItem disabled={true} style={{textAlign:'right'}}>Total  {Math.ceil(total*100)/100} CHF</MenuItem>{
                        this.props.currentUser.token ?
                            <MenuItem onClick={this.handlPurchaseProducts} style={{textAlign:'center'}}>Checkout</MenuItem>
                        :
                            <MenuItem disabled={true} style={{textAlign:'center'}}>Checkout</MenuItem>
                        }
                </Drawer>
            </div>
        );
    }
}

export default connect(mapStateToProps)(ShoppingCart);