use GREENSCORE;
db.GlobalConfiguration.drop();
db.GlobalConfiguration.insert( [
    {
        _id: "1",
        global_note: 6000,
        sections : [
            {name: "Architecture", default_weight: 0.25},
            {name: "Design", default_weight: 0.4},
            {name: "Usage", default_weight: 0.25},
            {name: "Logs", default_weight: 0.1},
        ],
        categories : [
            {letter: "A", name: "Excellent", range_min: 6000},
            {letter: "B", name: "Acceptable", range_min: 3000, range_max: 5999},
            {letter: "C", name: "Average", range_min: 2000, range_max: 2999},
            {letter: "D", name: "Poor", range_min: 1000, range_max: 1999},
            {letter: "E", name: "Very Poor", range_min: 0, range_max: 999},
            {letter: "NoEval", name: "Not evaluated"},
        ]
    }
]);

db.Rules.drop();
db.Rules.insertMany([
    {
        _id: "AR01",
        title: "Use Event Driven Architecture to avoid polling madness and inform subscribers of an update",
        description: "Use Event Driven Architecture to avoid polling madness.",
        default_weight: 0.25
    },
    {
        _id: "AR02",
        title: "API runtime close to the Consumer",
        description: "Deploy the API near the consumer",
        default_weight: 0.25
    },
    {
        _id: "AR03",
        title: "Ensure the same API does not exist *",
        description: "Ensure only one API fit the same need",
        default_weight: 0.25
    },
    {
        _id: "AR04",
        title: "Use scalable infrastructure to avoid over-provisioning",
        description: "Use scalable infrastructure to avoid over-provisioning",
        default_weight: 0.25
    },
    {
        _id: "DE01",
        title: "Choose an exchange format with the smallest size (JSON is smallest than XML)",
        description: "Prefer an exchange format with the smallest size (JSON is smaller than XML).",
        default_weight: 0.25
    },
    {
        _id: "DE02",
        title: "new API --> cache usage",
        description: "Use cache to avoid useless requests and preserve compute resources.",
        default_weight: 0.15
    },
    {
        _id: "DE03",
        title: "Existing API --> cache usage efficiency",
        description: "Use the cache efficiently to avoid useless resources consumtion.",
        default_weight: 0.2
    },
    {
        _id: "DE04",
        title: "Opaque token usage",
        description: "Prefer opaque token usage prior to JWT",
        default_weight: 0.02
    },
    {
        _id: "DE05",
        title: "Align the cache refresh with the datasource **",
        description: "Align cache refresh strategy with the data source",
        default_weight: 0.04
    },
    {
        _id: "DE06",
        title: "Allow part refresh of cache",
        description: "Allow a part cache refresh",
        default_weight: 0.04
    },
    {
        _id: "DE07",
        title: "Is System,  Business or cx API ?",
        description: "Use Business & Cx APIs closer to the business need",
        default_weight: 0.1
    },
    {
        _id: "DE08",
        title: "Possibility to filter results",
        description: "Implement filtering mechanism to limit the payload size",
        default_weight: 0.025
    },
    {
        _id: "DE09",
        title: "Leverage OData or GraphQL for your databases APIs",
        description: "Leverage OData or GraphQL when relevant",
        default_weight: 0.1
    },
    {
        _id: "DE10",
        title: "Redundant data information in the same API",
        description: "Avoid redundant data information in the same API",
        default_weight: 0.05
    },
    {
        _id: "DE11",
        title: "Possibility to fitler pagination results",
        description: "Implement pagination mechanism to limit the payload size",
        default_weight: 0.025
    },
    {
        _id: "US01",
        title: "Use query parameters for GET Methods",
        description: "Implement filters to limit which data are returned by the API (send just the data the consumer need).",
        default_weight: 0.05
    },
    {
        _id: "US02",
        title: "Decomission end of life or not used APIs",
        description: "Decomission end of life or not used APIs",
        default_weight: 0.1
    },
    {
        _id: "US03",
        title: "Number of API version <=2",
        description: "Compute resources saved & Network impact reduced",
        default_weight: 0.1
    },
    {
        _id: "US04",
        title: "Usage of Pagination of results available",
        description: "Optimize queries to limit the information returned to what is strictly necessary.",
        default_weight: 0.1
    },
    {
        _id: "US05",
        title: "Choosing relevant data representation (user don’t need to do multiple calls) is Cx API ?",
        description: "Choose the correct API based on use case to avoid requests on multiple systems or large number of requests. Refer to the data catalog to validate the data source.",
        default_weight: 0.2
    },
    {
        _id: "US06",
        title: "Number of Consumers",
        description: "Deploy an API well designed and documented to increase the reuse rate. Rate based on number of different consumers",
        default_weight: 0.25
    },
    {
        _id: "US07",
        title: "Error rate",
        description: "Monitor and decrease the error rate to avoid over processing",
        default_weight: 0.2
    },
    {
        _id: "LO01",
        title: "Logs retention",
        description: "Align log retention period to the business need (ops and Legal)",
        default_weight: 1
    }

]);
