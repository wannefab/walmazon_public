import React, { Component } from 'react';
import { connect } from 'react-redux';

import TextField from 'material-ui/TextField';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton';
import RaisedButton from 'material-ui/RaisedButton';
import {Tabs, Tab} from 'material-ui/Tabs';

import './index.css';
import { loginUser, registerUser } from '../../store/actions';
import mapStateToProps from './mapStateToProps';


class Login extends Component {

  constructor(props) {
    super(props);
    this.state = {
      //TAB SELECTOR
      tab: 'login',

      //LOGIN
      eMail: '',
      eMailError: '',
      password: '',
      passwordError: '',

      //REGISTRATION
      registeringTitle: "Mr.",
      registeringFirstName: '',
      registeringFirstNameError: '',
      registeringLastName: '',
      registeringLastNameError: '',
      registeringAddress: '',
      registeringAddressError: '',
      registeringCity: '',
      registeringCityError: '',
      registeringBirthDateYear: '',
      registeringBirthDateYearError: '',
      registeringBirthDateMonth: '',
      registeringBirthDateMonthError: '',
      registeringBirthDateDay: '',
      registeringBirthDateDayError: '',
      registeringUsername: '',
      registeringUsernameError: '',
      registeringEMail: '',
      registeringEMailError: '',
      registeringPassword: '',
      registeringPasswordError: '',
      registeringPassword2: '',
      registeringPassword2Error: '',
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
      this.props.dispatch(loginUser(this.state.eMail,this.state.password))
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
	//------ REGISTERING -------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

  handleRegisteringTitleChange = (event) => {
    this.setState({
      registeringTitle: event.target.value,
    });
  };

  handleRegisteringFirstNameChange = (event) => {
    this.setState({
      registeringFirstName: event.target.value,
    });
  };

  handleRegisteringLastNameChange = (event) => {
    this.setState({
      registeringLastName: event.target.value,
    });
  };

  handleRegisteringAddressChange = (event) => {
    this.setState({
      registeringAddress: event.target.value,
    });
  };

  handleRegisteringCityChange = (event) => {
    this.setState({
      registeringCity: event.target.value,
    });
  };

  handleRegisteringBirthDateYearChange = (event) => {
    this.setState({
      registeringBirthDateYear: event.target.value,
    });
  };

  handleRegisteringBirthDateMonthChange = (event) => {
    this.setState({
      registeringBirthDateMonth: event.target.value,
    });
  };

  handleRegisteringBirthDateDayChange = (event) => {
    this.setState({
      registeringBirthDateDay: event.target.value,
    });
  };

  handleRegisteringUsernameChange = (event) => {
    this.setState({
      registeringUsername: event.target.value,
    });
  };

  handleRegisteringEMailChange = (event) => {
    this.setState({
      registeringEMail: event.target.value,
    });
  };

  handleRegisteringPasswordChange = (event) => {
    this.setState({
      registeringPassword: event.target.value,
    });
  };

  handleRegisteringPassword2Change = (event) => {
    this.setState({
      registeringPassword2: event.target.value,
    });
  };

  handleRegister = () => {
    if(this.checkRegistrationForm()){
      let birthDate = new Date(this.state.registeringBirthDateYear, this.state.registeringBirthDateMonth-1, this.state.registeringBirthDateDay, 2, 0, 0, 0);
      
      this.props.dispatch(registerUser(this.state.registeringTitle, this.state.registeringFirstName, this.state.registeringLastName, this.state.registeringAddress, this.state.registeringCity, birthDate, this.state.registeringUsername, this.state.registeringEMail,this.state.registeringPassword))
      .then((data)=>{
        if(data.status === 200){
          this.handleTabChange('login')
        }
        else if(data.status === 409){
          this.setState({
            registeringEMailError: 'Email allready in use!',
          });
        }
      });
    }
  };

  checkRegistrationForm = () => {
    let isValid = true;

    let today = new Date();
    if( !Number.isInteger(parseInt(this.state.registeringBirthDateYear,10)) || parseInt(this.state.registeringBirthDateYear,10)<1850 || parseInt(this.state.registeringBirthDateYear,10)>today.getFullYear() ){
      isValid = false;
    }

    if( !Number.isInteger(parseInt(this.state.registeringBirthDateMonth,10)) || parseInt(this.state.registeringBirthDateMonth,10)<1 || parseInt(this.state.registeringBirthDateMonth,10)>12 ){
      isValid = false;
    }

    if( !Number.isInteger(parseInt(this.state.registeringBirthDateDay,10)) || parseInt(this.state.registeringBirthDateDay,10)<1 || parseInt(this.state.registeringBirthDateDay,10)>31 ){
      isValid = false;
    }

    if(!isValid){
      this.setState({
        registeringBirthDateYearError: 'year (1967,2001)',
        registeringBirthDateMonthError: 'month (2,12)',
        registeringBirthDateDayError: 'day (12,28)',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringBirthDateYearError: '',
        registeringBirthDateMonthError: '',
        registeringBirthDateDayError: '',
      });
    }

    if(this.state.registeringFirstName === ''){
      this.setState({
        registeringFirstNameError: 'first name must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringFirstNameError: '',
      });
    }

    if(this.state.registeringLastName === ''){
      this.setState({
        registeringLastNameError: 'last name must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringLastNameError: '',
      });
    }

    if(this.state.registeringAddress === ''){
      this.setState({
        registeringAddressError: 'address must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringAddressError: '',
      });
    }

    if(this.state.registeringCity === ''){
      this.setState({
        registeringCityError: 'city must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringCityError: '',
      });
    }

    if(this.state.registeringUsername === ''){
      this.setState({
        registeringUsernameError: 'username must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringUsernameError: '',
      });
    }

    if(this.state.registeringEMail === ''){
      this.setState({
        registeringEMailError: 'e-mail name must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringEMailError: '',
      });
    }

    if(this.state.registeringPassword === ''){
      this.setState({
        registeringPasswordError: 'password name must be set!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringPasswordError: '',
      });
    }

    if(this.state.registeringPassword !== this.state.registeringPassword2){
      this.setState({
        registeringPassword2Error: 'passwords do not match!',
      });
      isValid = false;
    }
    else{
      this.setState({
        registeringPassword2Error: '',
      });
    }

    return isValid;
  };


  //--------------------------------------------------------------------------------------------
	//------ TABS -------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

  handleTabChange = (value) => {
    this.setState({
      value: value,
    });
  };


	//--------------------------------------------------------------------------------------------
	//------ RENDER ------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------
  
  render() {
    return (
      <div className="tabContainer">
        <div className="tabForm">
          <Tabs
            value={this.state.value}
            onChange={this.handleTabChange}
          >
            <Tab label="Login" value="login">
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
            </Tab>
            <Tab label="Register" value="register">
              <div className="loginContainer">
                <div className="loginForm">
                  
                  <RadioButtonGroup 
                    style={{marginTop:30,marginBottom:-10,display: 'flex'}} 
                    name="Title" 
                    defaultSelected="Mr."
                    onChange={this.handleRegisteringTitleChange}
                  >
                    <RadioButton
                      value="Mr."
                      label="Mr."
                    />
                    <RadioButton
                      value="Mrs."
                      label="Mrs."
                    />
                  </RadioButtonGroup>

                  <TextField
                    hintText="Enter your First Name here"
                    errorText={this.state.registeringFirstNameError}
                    floatingLabelText="First Name"
                    fullWidth={true}
                    onChange={this.handleRegisteringFirstNameChange}
                  />
                  <TextField
                    hintText="Enter your Last Name here"
                    errorText={this.state.registeringLastNameError}
                    floatingLabelText="Last Name"
                    fullWidth={true}
                    onChange={this.handleRegisteringLastNameChange}
                  />
                  <TextField
                    hintText="Enter your Address here"
                    errorText={this.state.registeringAddressError}
                    floatingLabelText="Address"
                    fullWidth={true}
                    onChange={this.handleRegisteringAddressChange}
                  />
                  <TextField
                    hintText="Enter your Zipcode and City here"
                    errorText={this.state.registeringCityError}
                    floatingLabelText="Zipcode and City"
                    fullWidth={true}
                    onChange={this.handleRegisteringCityChange}
                  />
                  <div className="BirthDateInput">
                    <TextField
                      hintText="Year"
                      errorText={this.state.registeringBirthDateYearError}
                      floatingLabelText="Birth Date"
                      style={{width:'30%',marginRight:"5%"}}
                      onChange={this.handleRegisteringBirthDateYearChange}
                    />
                    <TextField
                      hintText="Month"
                      errorText={this.state.registeringBirthDateMonthError}
                      floatingLabelText="Birth Date"
                      style={{width:'30%',marginRight:"5%"}}
                      onChange={this.handleRegisteringBirthDateMonthChange}
                    />
                    <TextField
                      hintText="Day"
                      errorText={this.state.registeringBirthDateDayError}
                      floatingLabelText="Birth Date"
                      style={{width:'30%'}}
                      onChange={this.handleRegisteringBirthDateDayChange}
                    />
                  </div>
                  <TextField
                    hintText="Enter your Username here"
                    errorText={this.state.registeringUsernameError}
                    floatingLabelText="Username"
                    fullWidth={true}
                    onChange={this.handleRegisteringUsernameChange}
                  />
                  <TextField
                    hintText="Enter your E-Mail here"
                    errorText={this.state.registeringEMailError}
                    floatingLabelText="Email"
                    fullWidth={true}
                    onChange={this.handleRegisteringEMailChange}
                  />
                  <TextField
                    hintText="Enter your Password here"
                    errorText={this.state.registeringPasswordError}
                    floatingLabelText="Password"
                    type="password"
                    fullWidth={true}
                    onChange={this.handleRegisteringPasswordChange}
                  />
                  <TextField
                    hintText="Reenter your Password here"
                    errorText={this.state.registeringPassword2Error}
                    floatingLabelText="Password"
                    type="password"
                    fullWidth={true}
                    onChange={this.handleRegisteringPassword2Change}
                  />
                  <RaisedButton 
                    label="Create Account" 
                    primary={true} 
                    margin='12px' 
                    fullWidth={true}
                    onClick={this.handleRegister}
                  />
                </div>
              </div>
          </Tab>
          </Tabs>
          </div>
      </div>
      
    );
  }

}
  
export default connect(mapStateToProps)(Login);
  

