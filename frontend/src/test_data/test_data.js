export function contents(param) {
    return {
        title: param,
        summary: 'Summary text of ' + param,
        content: [
            {
                label: '1 History',
                children: [
                    {
                        label: '1.1 Ancient History'
                    },
                    {
                        label: '1.2 Modern History'
                    }
                ]
            },
            {
                label: '2 Features',
                children: [
                    {
                        label: '2.1 Advantages',
                        children: [
                            {
                                label: '2.1.1 Good water-absorbing quality',
                                text: 'text...'
                            },
                            {
                                label: '2.1.2 Good air-permability',
                                text: 'text...'
                            }
                        ]
                    },
                    {
                        label: '2.2 Disadvantages'
                    }
                ]
            },
            {
                label: '3 Principles'
            }
        ],
        text: [
            {
                sectionTitle: 'History',
                sectionText: ''
            },
            {
                sectionTitle: 'Ancient History',
                sectionText: ''
            },
            {
                sectionTitle: 'Modern History',
                sectionText: ''
            },
            {
                sectionTitle: 'Features',
                sectionText: ''
            },
            {
                sectionTitle: 'Advantages',
                sectionText: ''
            },
            {
                sectionTitle: 'Good water-absorbing quality',
                sectionText: ''
            },
            {
                sectionTitle: 'Good air-permability',
                sectionText: ''
            },
            {
                sectionTitle: 'Disadvantages',
                sectionText: ''
            },
            {
                sectionTitle: 'Principles',
                sectionText: ''
            },
        ]
    };
}
