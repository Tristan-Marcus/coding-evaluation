package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization
{
    private Position root;

    // Declare the employee ID
    private int EmployeeID;

    public Organization()
    {
        root = createOrganization();

        // Initialize employee ID
        EmployeeID = 0;
    }

    protected abstract Position createOrganization();

    /**
     * hire the given person as an employee in the position that has that title
     *
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire( Name person, String title )
    {
        // Return the hired employee's position
        return checkPosition( root, person, title );
    }

    private Optional<Position> checkPosition( Position position, Name person, String title )
    {
        // Set the position to empty by default
        Optional<Position> positionToHire = Optional.empty();

        // Check if the title exists in the Organization
        if( position.getTitle().equals( title ) )
        {
            // Create an employee
            Employee newHire = new Employee( EmployeeID, person );

            // Hire the given employee to that position
            position.setEmployee( Optional.of( newHire ) );

            // Increment the Employee ID for the next new hire
            EmployeeID += 1;

            // Update the newly filled position
            positionToHire = Optional.of( position );
        }

        // If there is no title, Check if that position has direct reports
        if( !( position.getDirectReports().isEmpty() ) )
        {
            // Loop over each position that is a direct report
            for( Position currentPosition : position.getDirectReports() )
            {
                // Using Recursion, Check to see if the title exists
                positionToHire = checkPosition( currentPosition, person, title );

                // If the position doesn't exist
                if( positionToHire.isPresent() )
                {
                    // Return the newly hired position
                    return positionToHire;
                }
            }
        }

        // Return the newly filled position
        return positionToHire;
    }

    @Override
    public String toString()
    {
        return printOrganization(root, "");
    }

    private String printOrganization(Position pos, String prefix)
    {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports())
        {
            sb.append(printOrganization(p, prefix + "\t"));
        }

        return sb.toString();
    }
}
