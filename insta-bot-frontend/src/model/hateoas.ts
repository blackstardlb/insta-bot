export interface Link {
    href: string;
    templated: boolean;
}

export interface Links {
}

export interface Embedded {
}

export interface EmbeddedResource {
    _links: Links;
    _embedded: Embedded;
}
