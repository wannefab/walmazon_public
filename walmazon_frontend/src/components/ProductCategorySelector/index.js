import React, { Component } from 'react';

import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import FilterIcon from 'material-ui/svg-icons/content/filter-list';

class ProductCategorySelector extends Component {
    render() {
        return (
            <IconMenu
                iconButtonElement={<IconButton><FilterIcon color='white'/></IconButton>}
                onChange={this.props.handleFilterChange}
                value={this.props.filter}
                >
                <MenuItem value="" primaryText="All" />
                <MenuItem value="Robot" primaryText="Robots" />
                <MenuItem value="Monster" primaryText="Monsters" />
                <MenuItem value="Head" primaryText="Heads" />
                <MenuItem value="Cat" primaryText="Cats" />
            </IconMenu>
        )
    };
}

export default ProductCategorySelector;