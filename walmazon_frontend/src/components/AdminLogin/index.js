import React, { Component } from 'react';
import { connect } from 'react-redux';

import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

import './index.css';
import { loginAdmin } from '../../store/actions';
import mapStateToProps from './mapStateToProps';


class Login extends Component {

  constructor(props) {
    super(props);
    this.state = {
      eMail: '',
      eMailError: null,
      password: '',
      passwordError: null,
    };
  }


  //--------------------------------------------------------------------------------------------
	//------ LOGIN -------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------
  
  handleLoginEMailChange = (event) => {
    this.setState({
      eMail: event.target.value,
    });
  };

  handleLoginPasswordChange = (event) => {
    this.setState({
      password: event.target.value,
    });
  };

  handleLogin = () => {
    if(this.checkLoginForm()){
      this.props.dispatch(loginAdmin("admin","/sUp3rs3cUrepa55word."))
      //this.props.dispatch(loginUser(this.state.eMail,this.state.password))
      .then((data)=>{
        if(data.status === 200){
        }
        else {
          this.setState({
            eMailError: 'email or password wrong!',
            passwordError: 'email or password wrong!',
          });
        }
      });
    }
  };

  checkLoginForm = () => {
    let isValid = true;

    if(this.state.eMail === ''){
      this.setState({
        eMailError: 'email must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        eMailError: '',
      });
    }

   if(this.state.password === ''){
      this.setState({
        passwordError: 'password must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        passwordError: '',
      });
    }
    return isValid;
  }


	//--------------------------------------------------------------------------------------------
	//------ RENDER ------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------
  
  render() {
    return (
      <div className="loginContainer">
        <div className="loginForm">
          <TextField
            hintText="Enter your E-Mail here"
            errorText={this.state.eMailError}
            floatingLabelText="Email"
            fullWidth={true}
            onChange={this.handleLoginEMailChange}
          />
          <TextField
            hintText="Enter your Password here"
            errorText={this.state.passwordError}
            floatingLabelText="Password"
            type="password"
            fullWidth={true}
            onChange={this.handleLoginPasswordChange}
          />
          <RaisedButton 
            label="Login" 
            primary={true} 
            margin='12px' 
            fullWidth={true}
            onClick={this.handleLogin}
          />
        </div>
      </div>
    );
  }

}
  
export default connect(mapStateToProps)(Login);
  

