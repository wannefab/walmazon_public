# Walmazon

Time to shine full-stackers!! 

A full stack project has to be build in a week and now you are all by your own. 

But don't worry about the work load because now the concept of reusability will be finally applied.

This project is about have aws many features as possible so don't worry about perfectionism.

## Technology used

Same as last project:
* React.js && (MaterialUI || BlueprintJS)
* Spring && H2 || Node && Express && (PostgreSQL || MongoDB) 

## Product Features

This time you have more freedom to define the architecture of your application. You just need to cover these features:

- Home Page
  - Contains carousel of different categories to flip through
- Product Search
  - Products can be search for either globally or in a specific category
- Item View
  - From this view, the user can add star ratings and written review of the product, see a description, picture of the product, and item stock, and add the item to their basket (if it is in stock)
- Basket View
  - This view shows items that have been added to the basket, or basket is empty if there are no items
  - Items can be interact with by deleting, restoring, or removing entirely
- Sign-in/Log-in:
  - User can create an account if it has not.
  - User can log in if it has already one.
- User's profile - **Private**
  - User chan modify his/her profile.
- Checkout View - **Private**
  - In order to checkout, user has to be login
  - Card details can be entered here to prepare for payment
  - Also ship address will be taken from the profile but user could still change it.
- Payment Status View - **Private**
  - This will display whether the payment failed or was successful
  - If succesful user will get an email with information about his/her order

### Tons of data

This time a couple of rows with data will not be enough. We want it big this time. Generate 10000 entries for your API and see how they behave now. 
* [Mockaro](https://www.mockaroo.com/) with `format` set to SQL or JSON depending your database.

## Style guide & Conventions

### JS style conventions

* 2 space indentation
* Semi-colons
* At most 1 blank line between blocks of code

```js
function helloWorld() {
  console.log('hi');
}

function helloWorld2() {
  console.log('hi');
}
```

### CSS style guide
[Styleguide](https://styleguide.mockflow.com/view/walmazon)

![Styleguide](http://res.cloudinary.com/yhabib/image/upload/v1510944577/Screen_Shot_2017-11-17_at_7.48.53_PM_mlf1vd.png)

## Workflow

At the end of the week you should be able to present something so let's try follow a more pragmatic approach:
- Start with the backend by building your first API endpoint.
  - Test it with Postman.
- Build the rest of your endpoints but no log in or sign in yet.
- Implement the views for the working endpoints.
  - Basic styling at this point focus on the functionality.
- Security functionality on the backend.
- Views for sign in and login.
- Email notifications.

### Considerations: 
- Reuse as much code as possible from last week.
  - If you have questions about your partners code, ask him but try to avoid breaking his/her workflow. Take advantange of his/her breaks.
- Try to target for small units of functionality. Don't try to build the whole APP at once.
- In order to track your list of **todos** use GitHub issues.
