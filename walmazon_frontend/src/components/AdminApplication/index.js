import React, { Component } from 'react';
import { connect } from 'react-redux';

import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow,
    TableRowColumn,
  } from 'material-ui/Table';

import AccountIcon from 'material-ui/svg-icons/action/account-box';
import DoneIcon from 'material-ui/svg-icons/action/done';

import './index.css';
import mapStateToProps from './mapStateToProps';
import AdminLogin from './../AdminLogin';
import { logoutUser, fetchOrders } from '../../store/actions';

var _colors = require('material-ui/styles/colors');

class Application extends Component {

    handleChangeStatus = (cartItem, status) => {
        //TODO: Change the status of an order when an order got processed
    }

    handleLogout = () => {
        this.props.dispatch(logoutUser());
    };


    render() {   
        if(this.props.orders.length===0 && this.props.currentUser.token){
            this.props.dispatch(fetchOrders());
        }

        return (
            <div>
                <AppBar
                    style={{'backgroundColor':_colors.purple800}}
                    titleStyle={{textAlign:'center',fontSize:'3vw'}}
                    title={ "Orders" }
                    iconElementLeft={<div></div>}
                    iconElementRight={
                        this.props.currentUser.token ?
                            <IconButton tooltip="Logout"  onClick={this.handleLogout} touch={true} tooltipPosition="bottom-left"><AccountIcon color="white"/></IconButton>
                        :
                        <IconMenu
                            iconButtonElement={<IconButton><AccountIcon color="white"/></IconButton>}
                            targetOrigin={{horizontal: 'right', vertical: 'top'}}
                            anchorOrigin={{horizontal: 'right', vertical: 'top'}}
                            >
                            <AdminLogin/>
                        </IconMenu>
                        }
                />
                <Table onRowSelection={this.handleRowSelection}>
                    <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                        <TableHeaderColumn style={{width:20,paddingRight:10,paddingLeft:10}}></TableHeaderColumn>
                        <TableHeaderColumn style={{width:30,paddingRight:10,paddingLeft:5}}>ID</TableHeaderColumn>
                        <TableHeaderColumn style={{width:40,paddingRight:10,paddingLeft:5}}>Pic</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>Name</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>Quantity</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>Total Price</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>First Name</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>Last Name</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>Address</TableHeaderColumn>
                        <TableHeaderColumn style={{paddingRight:10,paddingLeft:5}}>City</TableHeaderColumn>
                    </TableRow>
                    </TableHeader>
                    <TableBody displayRowCheckbox={false}>
                        {this.props.orders.map((order, index) => (
                            <TableRow key={index} >
                                <TableRowColumn style={{width:20,paddingRight:10,paddingLeft:10}}> <IconButton onClick={() => this.handleChangeStatusOpen(order,"shipped")}><DoneIcon/></IconButton> </TableRowColumn>
                                <TableRowColumn style={{width:30,paddingRight:10,paddingLeft:5}}>{order.product.id}</TableRowColumn>
                                <TableRowColumn style={{width:40,paddingRight:10,paddingLeft:5}}><img src={order.product.images} alt={"Loading..."}/></TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.product.name}</TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.quantity}</TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.product.price*order.quantity} CHF</TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.user.firstName}</TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.user.lastName}</TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.user.address}</TableRowColumn>
                                <TableRowColumn style={{paddingRight:10,paddingLeft:5}}>{order.user.city}</TableRowColumn>
                            </TableRow>
                        ))}         
                    </TableBody>
                </Table>
            </div>
        );
    }

}
  
export default connect(mapStateToProps)(Application);
  

