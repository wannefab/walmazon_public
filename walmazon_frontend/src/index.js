import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
import walmazonTheme from './walmazon_theme.js';
import getMuiTheme from 'material-ui/styles/getMuiTheme';

import './index.css';
import store from './store';
import Application from './components/Application';
import AdminApplication from './components/AdminApplication';


injectTapEventPlugin();

ReactDOM.render(
    <Provider store={ store }>
      <MuiThemeProvider muiTheme={getMuiTheme(walmazonTheme)}>
        <Router>
          <Switch>
            <Route exact path="/" component={ Application } />
            <Route exact path="/admin/" component={ AdminApplication } />
          </Switch>
        </Router>
      </MuiThemeProvider>
    </Provider>,
    document.getElementById('root')
  );
  