import * as React from 'react'
import { withStyles, WithStyles } from '@material-ui/core/styles'
import Paper from '@material-ui/core/Paper'
import Typography from '@material-ui/core/Typography'

import { simpleData, metaData } from './dataLibrary/simpleData'

import getStructureMetadata from './utils/getStructureMetadata'
import getCsvRows from './utils/getCsvRows'
import exportToCsv from './utils/exportToCsv'

const styles = () => ({
    root: {
      paddingTop: '3rem',
      paddingBottom: '3rem',
    }
})

export interface Props extends WithStyles<typeof styles> {
}

export class Converter extends React.Component<Props> {
    private exportCSV = () => {
        const struc = getStructureMetadata(metaData)
        const rows = getCsvRows(simpleData, struc)
        exportToCsv('test.csv', rows)
    }

    render() {
        const { classes } = this.props
        return (
            <Paper
                className={classes.root} elevation={1}
            >
                <Typography variant="h5" component="h3" onClick={this.exportCSV}>
                    convert
                </Typography>
            </Paper>
        )
    }
}

export default withStyles(styles)(Converter)
