# For Homework 3
## Server  DB
- Either JDBC or JPA
    - Research options, tradeoffs
- SERVER needs to keep track of:
    - Users (username, pw, activity indiciator)
        - Activity: # items sold, # items purchased
        - FOR GUI: Allow user to query for activity record of self
    - Items (name, price, amount, etc.)
        - Availability/status (in-progress, sold, removed)

## Modifications
- Registration, login/logout
    - Orchestrate with bank accounts, marketplace accounts
    - i.e. register only unique names, maintain login status
    - Username and password checks and alerts
    - Login verification & proceed to requested resources

## TODOS
- Research options & tradeoffs
- Determine security protocol (security token? session? etc.)
- Brainstorm design changes in GUI, server
    - Server should be easy to modify w/ repositories
    - Server needs login/security "layer"
    - GUI just needs some more buttons, login/registration flow (popups work well)
- [opt] Lookup-table/listening for wishes & notifications
- [opt] Clean up, refactor GUI code
- [opt] Implement DB caching layer


## Testing and Verification
- Should be able to stop and restart server with no loss of client data
- TODO: Should BANK also be backed by DB?

