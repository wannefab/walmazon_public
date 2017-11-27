import React, { Component } from 'react';
import { connect } from 'react-redux';

import IconButton from 'material-ui/IconButton';
import TextField from 'material-ui/TextField';
import SearchIcon from 'material-ui/svg-icons/action/search';

import mapStateToProps from './mapStateToProps';

const greetings = ['Hey','Hello','Welcome','Good to see you','Ciao','Nice to see you','Hiya'];
const shoppingQuotes = ['Just keep adding awesome Products to your Cart!','Theres still lots of room in your cart! ','Studys show that shopping makes you happy!','We have what YOU want!',"Keep Buying!","Happiness in a shopping cart!","Take it! =)","Seize the opportunity!","AWESOME Products!","Happiness at your fingertips!","Infinite possibilities!"];


class SearchBar extends Component {

    handleEnterOnSearchbar = (e) => {
        if(e.keyCode === 13){
            console.log(this.props.filter)
           this.props.handleFilter(this.props.filter)
        }
    }

    handleClickOnSearchbarIcon = () => {
        this.props.handleFilter(this.props.filter)
     }

    render() {
        return (
            this.props.currentUser.token ?
            <div className='SearchBar'> 
                <IconButton> <SearchIcon color="white" /> </IconButton>  
                <TextField 
                    onChange={this.props.handleSearchbarChange}
                    onKeyDown={this.handleEnterOnSearchbar}
                    style={{width:'80%'}} 
                    inputStyle={{color:'white',fontSize:'22'}} 
                    hintStyle={{color:'white',fontSize:'22'}} hintText={greetings[Math.floor(Math.random()*greetings.length)]+" "+this.props.currentUser.user.firstName+" - "+shoppingQuotes[Math.floor(Math.random()*shoppingQuotes.length)]}
                /> 
            </div>
            :
            <div className='SearchBar'> 
                <IconButton onClick={this.handleClickOnSearchbarIcon}> <SearchIcon color="white" /> </IconButton>  
                <TextField 
                    onChange={this.props.handleSearchbarChange}
                    onKeyDown={this.handleEnterOnSearchbar}
                    style={{width:'80%'}} 
                    inputStyle={{color:'white',fontSize:'22'}} 
                    hintStyle={{color:'white',fontSize:'22'}} hintText={"Walmazon - "+shoppingQuotes[Math.floor(Math.random()*shoppingQuotes.length)]}
                /> 
            </div>
        )
    };
}

export default connect(mapStateToProps)(SearchBar);